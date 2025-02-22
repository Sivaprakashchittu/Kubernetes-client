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
package io.kubernetes.client.util.credentials;

import static io.kubernetes.client.util.TestUtils.getApiKeyAuthFromClient;
import static org.assertj.core.api.Assertions.assertThat;

import io.kubernetes.client.openapi.ApiClient;
import org.junit.Test;

public class AccessTokenAuthenticationTest {

  @Test
  public void testTokenProvided() {
    final ApiClient client = new ApiClient();
    new AccessTokenAuthentication("token").provide(client);
    assertThat(getApiKeyAuthFromClient(client).getApiKeyPrefix()).isEqualTo("Bearer");
    assertThat(getApiKeyAuthFromClient(client).getApiKey()).isEqualTo("token");
  }

  @Test(expected = NullPointerException.class)
  public void testTokenNonnull() {
    new AccessTokenAuthentication(null);
  }
}
