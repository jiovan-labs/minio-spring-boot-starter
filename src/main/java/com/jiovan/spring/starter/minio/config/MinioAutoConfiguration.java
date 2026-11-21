/*
 * Copyright (c) 2020 Jiovan Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiovan.spring.starter.minio.config;


import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jiovan.plexus.spring.toolkit.property.MinioProperties;
import com.jiovan.plexus.spring.toolkit.util.MinioConfigWraps;
import io.minio.MinioClient;


/**
 * Configuration for minio client
 *
 * @author David Hsing
 *
 * @see "https://min.io"
 *
 * @see io.minio.MinioClient
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = MinioAutoConfiguration.PROPERTIES_PREFIX, name = "endpoint")
@ConditionalOnClass(value = MinioClient.class)
public class MinioAutoConfiguration {
    public static final String PROPERTIES_PREFIX = "jiovan.minio";    // $NON-NLS-1$
    public static final String MINIO_PROPERTIES = "minioProperties";    // $NON-NLS-1$
    public static final String MINIO_CLIENT = "minioClient";    // $NON-NLS-1$

    /**
     * Returns a new {@link com.jiovan.plexus.spring.toolkit.property.MinioProperties} bound to the {@code jiovan.minio} properties prefix
     *
     * @return a new {@link com.jiovan.plexus.spring.toolkit.property.MinioProperties}
     */
    @Bean(name = MINIO_PROPERTIES)
    @ConfigurationProperties(prefix = PROPERTIES_PREFIX)
    @ConditionalOnMissingBean(name = MINIO_PROPERTIES)
    public MinioProperties minioProperties() {
        return new MinioProperties();
    }

    /**
     * Returns the {@link io.minio.MinioClient} built from the given MinIO properties
     *
     * @param properties the MinIO properties providing the endpoint, credentials and connection settings
     *
     * @return a configured {@link io.minio.MinioClient}
     *
     * @throws Exception if the client cannot be built from the given properties
     */
    @Bean(name = MINIO_CLIENT)
    @ConditionalOnMissingBean(name = MINIO_CLIENT)
    public MinioClient minioClient(@Nonnull MinioProperties properties) throws Exception {
        return MinioConfigWraps.minioClient(properties);
    }
}
