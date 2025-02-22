/*
Copyright 2020 The Kubernetes Authors.
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
package io.kubernetes.client.util;

import static org.assertj.core.api.Assertions.assertThat;

import io.kubernetes.client.Resources;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import io.kubernetes.client.persister.FilePersister;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/** Tests for the FilePersister helper class */
public class FilePersisterTest {

  private static final String KUBECONFIG_FILE_PATH = Resources.getResource("kubeconfig").getPath();

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void testPersistence() throws IOException {
    File file = folder.newFile("testconfig");
    FilePersister fp = new FilePersister(file.getPath());

    KubeConfig config = KubeConfig.loadKubeConfig(new FileReader(KUBECONFIG_FILE_PATH));

    fp.save(
        config.getContexts(),
        config.getClusters(),
        config.getUsers(),
        config.getPreferences(),
        config.getCurrentContext());

    KubeConfig configOut = KubeConfig.loadKubeConfig(new FileReader(file));

    assertThat(configOut.getCurrentContext()).isEqualTo(config.getCurrentContext());
    assertThat(configOut.getClusters()).isEqualTo(config.getClusters());
    assertThat(configOut.getContexts()).isEqualTo(config.getContexts());
    assertThat(configOut.getUsers()).isEqualTo(config.getUsers());
  }
}
