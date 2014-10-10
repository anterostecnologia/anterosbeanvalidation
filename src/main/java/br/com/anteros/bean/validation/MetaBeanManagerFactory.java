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

/**
 * Description: API class to hold a singleton of a {@link MetaBeanManager}
 * that implements the finder and registry interfaces for MetaBeans<br/>
 *
 * @see org.apache.bval.model.MetaBean
 * @see MetaBeanManager
 */
public class MetaBeanManagerFactory {
    private static MetaBeanManager manager = new MetaBeanManager();

    /**
     * global meta bean finder.
     * @return the singleton
     */
    public static MetaBeanFinder getFinder() {
        return manager;
    }


    /**
     * set global meta bean manager, that is responsible
     * for finding, caching, xml registry and enrichment algorithm.
     * @param finder
     */
    public static void setManager(MetaBeanManager finder) {
        manager = finder;
    }
}
