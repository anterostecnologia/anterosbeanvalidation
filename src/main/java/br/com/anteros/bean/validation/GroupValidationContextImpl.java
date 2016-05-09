/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.bean.validation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.anteros.bean.validation.groups.Group;
import br.com.anteros.bean.validation.groups.Groups;
import br.com.anteros.bean.validation.model.MetaBean;
import br.com.anteros.bean.validation.model.MetaProperty;
import br.com.anteros.bean.validation.resolver.CachingTraversableResolver;
import br.com.anteros.bean.validation.util.AccessStrategy;
import br.com.anteros.bean.validation.util.FieldAccess;
import br.com.anteros.bean.validation.util.NodeImpl;
import br.com.anteros.bean.validation.util.PathImpl;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.persistence.metadata.annotation.Column;
import br.com.anteros.persistence.metadata.annotation.Columns;
import br.com.anteros.persistence.metadata.annotation.Label;
import br.com.anteros.validation.api.ConstraintValidator;
import br.com.anteros.validation.api.MessageInterpolator;
import br.com.anteros.validation.api.TraversableResolver;
import br.com.anteros.validation.api.metadata.ConstraintDescriptor;

/**
 * Description: instance per validation process, not thread-safe<br/>
 */
final class GroupValidationContextImpl<T> extends BeanValidationContext<ConstraintValidationListener<T>> implements
		GroupValidationContext<T>, MessageInterpolator.Context {

	private final MessageInterpolator messageResolver;
	private final PathImpl path;
	private final MetaBean rootMetaBean;

	/**
	 * the groups in the sequence of validation to take place
	 */
	private Groups groups;
	/**
	 * the current group during the validation process
	 */
	private Group currentGroup;

	private Class<?> currentOwner;

	private Map<String, Object> messageParameters = new HashMap<String, Object>();

	/**
	 * contains the validation constraints that have already been processed
	 * during this validation routine (as part of a previous group match)
	 */
	private HashSet<ConstraintValidatorIdentity> validatedConstraints = new HashSet<ConstraintValidatorIdentity>();

	private ConstraintValidation<?> constraintValidation;
	private final TraversableResolver traversableResolver;

	/**
	 * Create a new GroupValidationContextImpl instance.
	 * 
	 * @param listener
	 * @param aMessageResolver
	 * @param traversableResolver
	 * @param rootMetaBean
	 */
	public GroupValidationContextImpl(ConstraintValidationListener<T> listener, MessageInterpolator aMessageResolver,
			TraversableResolver traversableResolver, MetaBean rootMetaBean) {
		// inherited variable 'validatedObjects' is of type:
		// HashMap<GraphBeanIdentity, Set<PathImpl>> in this class
		super(listener, new HashMap<GraphBeanIdentity, Set<PathImpl>>());
		this.messageResolver = aMessageResolver;
		this.traversableResolver = CachingTraversableResolver.cacheFor(traversableResolver);
		this.rootMetaBean = rootMetaBean;
		this.path = PathImpl.create(null);
	}

	public void makeLabelDescriptor() {
		AccessStrategy access = this.getAccess();
		String label = "";
		if (access instanceof FieldAccess) {
			Field field = ((FieldAccess) access).getField();
			if (field != null) {
				if (field.isAnnotationPresent(Columns.class)) {
					Columns columns = field.getAnnotation(Columns.class);
					if (!StringUtils.isEmpty(columns.label()))
						label = columns.label();
				} else if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					if (!StringUtils.isEmpty(column.label()))
						label = column.label();
				} else if (field.isAnnotationPresent(Label.class)) {
					Label lbl = field.getAnnotation(Label.class);
					if (!StringUtils.isEmpty(lbl.value()))
						label = lbl.value();
				}
			}
		}
		setMessageParameter("label", label);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentIndex(Integer index) {
		NodeImpl leaf = path.getLeafNode();
		if (leaf.getName() == null) {
			leaf.setIndex(index);
		} else {
			path.addNode(NodeImpl.atIndex(index));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentKey(Object key) {
		NodeImpl leaf = path.getLeafNode();
		if (leaf.getName() == null) {
			leaf.setKey(key);
		} else {
			path.addNode(NodeImpl.atKey(key));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveDown(MetaProperty prop, AccessStrategy access) {
		path.addProperty(prop.getName());
		super.moveDown(prop, access);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveUp(Object bean, MetaBean metaBean) {
		NodeImpl leaf = path.getLeafNode();
		if (leaf.isInIterable() && leaf.getName() != null) {
			leaf.setName(null);
		} else {
			path.removeLeafNode();
		}
		super.moveUp(bean, metaBean); // call super!
	}

	/**
	 * {@inheritDoc} Here, state equates to bean identity + group.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean collectValidated() {

		// Combination of bean+group+owner (owner is currently ignored)
		GraphBeanIdentity gbi = new GraphBeanIdentity(getBean(), getCurrentGroup().getGroup(), getCurrentOwner());

		Set<PathImpl> validatedPathsForGBI = (Set<PathImpl>) validatedObjects.get(gbi);
		if (validatedPathsForGBI == null) {
			validatedPathsForGBI = new HashSet<PathImpl>();
			validatedObjects.put(gbi, validatedPathsForGBI);
		}

		// If any of the paths is a subpath of the current path, there is a
		// circular dependency, so return false
		for (PathImpl validatedPath : validatedPathsForGBI) {
			if (path.isSubPathOf(validatedPath)) {
				return false;
			}
		}

		// Else, add the currentPath to the set of validatedPaths
		validatedPathsForGBI.add(PathImpl.copy(path));
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean collectValidated(ConstraintValidator<?, ?> constraint) {
		ConstraintValidatorIdentity cvi = new ConstraintValidatorIdentity(getBean(), getPropertyPath(), constraint);
		return this.validatedConstraints.add(cvi);
	}

	/**
	 * Reset the validated constraints.
	 */
	public void resetValidatedConstraints() {
		validatedConstraints.clear();
	}

	/**
	 * {@inheritDoc} If an associated object is validated, add the association
	 * field or JavaBeans property name and a dot ('.') as a prefix to the
	 * previous rules. uses prop[index] in property path for elements in
	 * to-many-relationships.
	 * 
	 * @return the path in dot notation
	 */
	public PathImpl getPropertyPath() {
		PathImpl currentPath = PathImpl.copy(path);
		if (getMetaProperty() != null) {
			currentPath.addProperty(getMetaProperty().getName());
		}
		return currentPath;
	}

	/**
	 * {@inheritDoc}
	 */
	public MetaBean getRootMetaBean() {
		return rootMetaBean;
	}

	/**
	 * Set the Groups.
	 * 
	 * @param groups
	 */
	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	/**
	 * {@inheritDoc}
	 */
	public Groups getGroups() {
		return groups;
	}

	/**
	 * {@inheritDoc}
	 */
	public Group getCurrentGroup() {
		return currentGroup;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCurrentGroup(Group currentGroup) {
		this.currentGroup = currentGroup;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setConstraintValidation(ConstraintValidation<?> constraint) {
		constraintValidation = constraint;
	}

	/**
	 * {@inheritDoc}
	 */
	public ConstraintValidation<?> getConstraintValidation() {
		return constraintValidation;
	}

	/**
	 * {@inheritDoc}
	 */
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return constraintValidation;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getValidatedValue() {
		makeLabelDescriptor();
		if (getMetaProperty() != null) {
			return getPropertyValue(constraintValidation.getAccess());
		} else {
			return getBean();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public MessageInterpolator getMessageResolver() {
		return messageResolver;
	}

	/**
	 * {@inheritDoc}
	 */
	public TraversableResolver getTraversableResolver() {
		return traversableResolver;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getCurrentOwner() {
		return this.currentOwner;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCurrentOwner(Class<?> currentOwner) {
		this.currentOwner = currentOwner;
	}

	public Map<String, Object> getMessageParameters() {
		return this.messageParameters;
	}

	public void setMessageParameter(String name, Object value) {
		this.messageParameters.put(name, value);
	}
}
