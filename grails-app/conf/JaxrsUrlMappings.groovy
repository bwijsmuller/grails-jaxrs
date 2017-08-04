/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.LogFactory

/**
 * Defined URL mapping for the JaxrsController. By default any URL is mapped to
 * the the JaxrsController i.e. other controllers are not accessible by default.
 * In order to map only a subset of possible URLs to JaxrsController an
 * additional entry must be made to <code>grails-app/conf/Config.groovy</code>.
 * For example, the entry
 *
 * <pre>
 * org.grails.jaxrs.url.mappings=['/test', '/notes']
 * </pre>
 *
 * only maps the URLs
 *
 * <ul>
 * <li><code>/test</code></li>
 * <li><code>/test/**</code></li>
 * <li><code>/notes</code></li>
 * <li><code>/notes/**</code></li>
 * <ul>
 *
 * to the JaxrsController.
 *
 * @author Martin Krasser
 */
class JaxrsUrlMappings {

    static mappings = { applicationContext ->
        def logger = LogFactory.getLog(JaxrsUrlMappings)
        def patternList = applicationContext?.grailsApplication?.config?.org?.grails?.jaxrs?.url?.mappings

        if (!patternList) {
            patternList = ['/api']
        } else if (patternList instanceof String) {
            patternList = [patternList]
        }

        logger.info('URL mappings for JaxrsController:')
        patternList.each { pattern ->
            "${pattern}"(controller:"jaxrs")
            "${pattern}/**"(controller:"jaxrs")
            logger.info("${pattern}")
            logger.info("${pattern}/**")
        }
		// WADL document generation at runtime
		"/application.wadl"(controller:"jaxrs")
	}
}