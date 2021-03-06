/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.runners.flink.translation.functions;

import java.io.Serializable;
import org.apache.beam.runners.core.construction.graph.ExecutableStage;
import org.apache.beam.runners.fnexecution.control.StageBundleFactory;
import org.apache.beam.runners.fnexecution.provisioning.JobInfo;
import org.apache.beam.runners.fnexecution.state.StateRequestHandler;
import org.apache.flink.api.common.functions.RuntimeContext;

/** The Flink context required in order to execute {@link ExecutableStage stages}. */
public interface FlinkExecutableStageContext {

  /**
   * Creates {@link FlinkExecutableStageContext} instances. Serializable so that factories can be
   * defined at translation time and distributed to TaskManagers.
   */
  interface Factory extends Serializable {
    FlinkExecutableStageContext get(JobInfo jobInfo);
  }

  static Factory batchFactory() {
    return BatchFlinkExecutableStageContext.BatchFactory.INSTANCE;
  }

  <InputT> StageBundleFactory<InputT> getStageBundleFactory(ExecutableStage executableStage);

  StateRequestHandler getStateRequestHandler(
      ExecutableStage executableStage, RuntimeContext runtimeContext);
}
