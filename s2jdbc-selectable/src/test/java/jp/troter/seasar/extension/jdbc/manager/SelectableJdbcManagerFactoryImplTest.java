/*
 * Copyright 2011 Takumi IINO
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
package jp.troter.seasar.extension.jdbc.manager;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SelectableJdbcManagerFactoryImplTest {

    SelectableJdbcManagerFactoryImpl i;

    @Before
    public void before() throws Exception {
        i = new SelectableJdbcManagerFactoryImpl();
        i.setDefaultJdbcManagerName("default");
    }

    @Test
    public void getJdbcManagerComponentName() {
        assertThat(i.getJdbcManagerComponentName(null), is("jdbcManager"));
        assertThat(i.getJdbcManagerComponentName("master"), is("masterJdbcManager"));
        assertThat(i.getJdbcManagerComponentName("slave1"), is("slave1JdbcManager"));
    }

    @Test
    public void empty() {
        i = new SelectableJdbcManagerFactoryImpl();

        assertThat(i.getJdbcManagerName(), nullValue());
        assertThat(i.getSelectableJdbcManagerNames().size(), is(0));
    }

    @Test
    public void add$remove$list() {
        assertThat(i.getDefaultJdbcManagerName(), is("default"));

        i.addSelectableJdbcManagerName("node1");
        i.addSelectableJdbcManagerName("node2");
        i.addSelectableJdbcManagerName("node3");
        assertThat(i.getSelectableJdbcManagerNames().size(), is(4));
        assertThat(i.getSelectableJdbcManagerNames(), hasItems("node1", "node2", "node3", "default"));

        i.removeSelectableJdbcManagerName("node2");
        assertThat(i.getSelectableJdbcManagerNames().size(), is(3));
        assertThat(i.getSelectableJdbcManagerNames(), hasItems("node1", "node3", "default"));
    }

    @Test
    public void getJdbcManagerName() {
        assertThat(i.getJdbcManagerName(), describedAs("何も設定されていない場合はdefaultを利用", is("default")));
        assertThat(i.getDefaultJdbcManagerName(), is("default"));

        i.setJdbcManagerName("not exists");
        assertThat(i.getJdbcManagerName(), describedAs("選択可能ではないコンポーネント名の場合はdefaultを利用", is("default")));
        assertThat(i.getDefaultJdbcManagerName(), is("default"));

        i.addSelectableJdbcManagerName("node1");
        i.addSelectableJdbcManagerName("node2");
        i.addSelectableJdbcManagerName("node3");

        i.setJdbcManagerName("node2");
        assertThat(i.getJdbcManagerName(), describedAs("選択可能なコンポーネント名の場合はそのコンポーネント名を利用", is("node2")));
        assertThat(i.getDefaultJdbcManagerName(), is("default"));

        i.setJdbcManagerName("default");
        assertThat(i.getJdbcManagerName(), describedAs("defaultを指定した場合はmasterを利用", is("default")));
        assertThat(i.getDefaultJdbcManagerName(), is("default"));

        i.setJdbcManagerName("node2");
        i.removeSelectableJdbcManagerName("node2");
        assertThat(i.getJdbcManagerName(), describedAs("削除された場合はdefaultを利用", is("default")));
        assertThat(i.getDefaultJdbcManagerName(), is("default"));
    }
}
