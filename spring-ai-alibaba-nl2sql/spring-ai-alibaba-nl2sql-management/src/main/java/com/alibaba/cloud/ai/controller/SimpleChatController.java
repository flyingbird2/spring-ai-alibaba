/**
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.controller;

import com.alibaba.cloud.ai.connector.config.DbConfig;
import com.alibaba.cloud.ai.request.SchemaInitRequest;
import com.alibaba.cloud.ai.service.simple.SimpleNl2SqlService;
import com.alibaba.cloud.ai.service.simple.SimpleVectorStoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class SimpleChatController {

	private final SimpleNl2SqlService simpleNl2SqlService;

	private final SimpleVectorStoreService simpleVectorStoreService;

	private final DbConfig dbConfig;

	public SimpleChatController(SimpleNl2SqlService simpleNl2SqlService,
			SimpleVectorStoreService simpleVectorStoreService, DbConfig dbConfig) {
		this.simpleNl2SqlService = simpleNl2SqlService;
		this.simpleVectorStoreService = simpleVectorStoreService;
		this.dbConfig = dbConfig;
	}

	@PostMapping("/simpleChat")
	public String simpleNl2Sql(@RequestBody String input) throws Exception {
		SchemaInitRequest schemaInitRequest = new SchemaInitRequest();
//		schemaInitRequest.setDbConfig(dbConfig);
//		SchemaInitRequest request = new SchemaInitRequest();
		schemaInitRequest.setDbConfig(new DbConfig());
		schemaInitRequest.getDbConfig().setUrl("jdbc:mysql://localhost:3306/nl2sql_database");
		schemaInitRequest.getDbConfig().setUsername("root");
		schemaInitRequest.getDbConfig().setPassword("qihf.123");
		schemaInitRequest.getDbConfig().setSchema("nl2sql_database");
		schemaInitRequest.getDbConfig().setDialectType("mysql");
		schemaInitRequest.setTables(Arrays.asList("agent"));
//		schemaInitRequest
//			.setTables(Arrays.asList("agent"));
		simpleVectorStoreService.schema(schemaInitRequest);
		return simpleNl2SqlService.nl2sql(input);
	}

}
