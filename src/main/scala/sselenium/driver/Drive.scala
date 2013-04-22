/*
  Copyright (C) 2013 the original author or authors.

  See the LICENSE.txt file distributed with this work for additional
  information regarding copyright ownership.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package sselenium.driver

import _root_.java.io.File
import org.seleniumhq.jetty7.server._
import com.dongxiguo.fastring.Fastring.Implicits._
import org.seleniumhq.jetty7.server.handler.ContextHandler
import org.openqa.jetty.jetty.servlet.WebApplicationContext
import org.openqa.selenium.remote.server.DriverServlet

/**
 */
class Drive {
  private implicit val (logger, formatter, appender) = ZeroLoggerFactory.newLogger

  logger.info(fast"Logging statement")

  val s = new Server(0)
  val app = new WebApplicationContext()
  app.setContextPath("/")
  app.setWAR(new File(".").getAbsolutePath)
  app.addServlet("/wd/*", classOf[DriverServlet].getName)
  //s.setHandler(app)

  val context = new ContextHandler("/")

  s.setHandler(context)
  s.addConnector(new LocalConnector().)
  s.start()
  val port = s.getConnectors()(0).getLocalPort
  logger.info(fast"PORT: $port")
  Thread.sleep(20 * 1000)
  s.stop()
  while(!s.isStopped)
    Thread.sleep(100)
}
