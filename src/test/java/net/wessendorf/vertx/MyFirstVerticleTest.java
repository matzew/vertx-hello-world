/*
 * Copyright (C) 2017 Matthias Weßendorf.
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
 */
 package net.wessendorf.vertx;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class MyFirstVerticleTest {

  private Vertx vertx;

  @Before
  public void setUp(TestContext context) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(MyFirstVerticle.class.getName(),
        context.asyncAssertSuccess());
  }

  @After
  public void tearDown(TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testMyApplication(TestContext context) {
    final Async async = context.async();

    vertx.createHttpClient().getNow(8181, "localhost", "/",
     response -> {
      response.handler(body -> {
        context.assertTrue(body.toString().contains("Hello"));
        async.complete();
      });
    });
  }
}