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
package io.kubernetes.client.apimachinery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;
import org.junit.Test;

public class GroupVersionTest {

  @Test
  public void parseV1() {
    GroupVersion left = new GroupVersion("", "v1");
    GroupVersion right = GroupVersion.parse(new V1Pod().apiVersion("v1"));
    assertThat(left).isEqualTo(right);
  }

  @Test
  public void parseAppsV1() {
    GroupVersion left = new GroupVersion("apps", "v1");
    GroupVersion right = GroupVersion.parse(new V1Deployment().apiVersion("apps/v1"));
    assertThat(left).isEqualTo(right);
  }

  @Test
  public void parseInvalid1() {
    assertThatThrownBy(() -> GroupVersion.parse(new V1Pod()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("apiVersion can not be null");
  }

  @Test
  public void parseInvalid2() {
    assertThatThrownBy(() -> GroupVersion.parse(new V1Pod().apiVersion(null)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("apiVersion can not be null");
  }

  @Test
  public void parseInvalid3() {
    assertThatThrownBy(() -> GroupVersion.parse(new V1Pod().apiVersion("foo/bar/f")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid apiVersion: foo/bar/f");
  }

  @Test
  public void testParseWithNullApiVersion() {
    V1Pod pod = new V1Pod().apiVersion(null);
    assertThatThrownBy(() -> GroupVersion.parse(pod))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("apiVersion can not be null");
  }

  @Test
  public void testParseWithSpecialCharacters() {
    String apiVersion = "my-group/special-v1.0";
    GroupVersion parsedGroupVersion = GroupVersion.parse(apiVersion);

    assertThat(parsedGroupVersion.getGroup()).isEqualTo("my-group");
    assertThat(parsedGroupVersion.getVersion()).isEqualTo("special-v1.0");
  }

  @Test
  public void parseVersionOnly() {
    GroupVersion result = GroupVersion.parse("v1");
    GroupVersion expected = new GroupVersion("", "v1");
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void testParseWithInvalidApiVersionStructure() {
    assertThatThrownBy(() -> GroupVersion.parse(new V1Pod()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid apiVersion: group/version/kind");
  }
  
}
