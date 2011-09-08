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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.manager.JdbcManagerImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.TestContext;

@RunWith(Seasar2.class)
public class SelectableJdbcManagerFactoryImplGetJdbcManagerTest {

    public static class FakeJdbcManager extends JdbcManagerImpl { }

    protected TestContext context;
    SelectableJdbcManagerFactoryImpl i;
    JdbcManager default_;
    JdbcManager node1;
    JdbcManager node2;

    public void before() {
        i = new SelectableJdbcManagerFactoryImpl();
        i.setDefaultJdbcManagerName("default");

        i.container = (S2Container)context.getComponent("container");

        context.register(default_ = new FakeJdbcManager(), "defaultJdbcManager");
        context.register(node1 = new FakeJdbcManager(), "node1JdbcManager");
        context.register(node2 = new FakeJdbcManager(), "noce2JdbcManager");
    }

    @Test
    public void smoke() {
        assertThat(i.getJdbcManager(), instanceOf(JdbcManager.class));
        assertThat(i.getJdbcManager(), is(default_));
    }

    @Test
    public void selectable() {
        i.addSelectableJdbcManagerName("node1");

        i.setJdbcManagerName("node1");
        assertThat(i.getJdbcManager().getClass(), typeCompatibleWith(JdbcManager.class));
        assertThat(i.getJdbcManager(), is(node1));

        i.setJdbcManagerName("not exists");
        assertThat(i.getJdbcManager().getClass(), typeCompatibleWith(JdbcManager.class));
        assertThat(i.getJdbcManager(), is(default_));

        i.setJdbcManagerName("default");
        assertThat(i.getJdbcManager().getClass(), typeCompatibleWith(JdbcManager.class));
        assertThat(i.getJdbcManager(), is(default_));
    }
}
