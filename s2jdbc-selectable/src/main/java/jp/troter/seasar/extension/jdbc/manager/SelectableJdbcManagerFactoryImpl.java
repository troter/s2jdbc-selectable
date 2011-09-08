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

import java.util.Set;

import jp.troter.seasar.extension.jdbc.SelectableJdbcManagerFactory;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;

public class SelectableJdbcManagerFactoryImpl implements SelectableJdbcManagerFactory {

    /**
     * デフォルトの<code>JdbcManager</code>のコンポーネント名。
     */
    protected String defaultJdbcManagerName;

    /**
     * 選択可能な<code>JdbcManager</code>のコンポーネント名一覧を管理します。
     */
    protected Set<String> selectableJdbcManagerNames = CollectionsUtil.newCopyOnWriteArraySet();

    /**
     * 動的な<code>JdbcManager</code>のコンポーネント名を管理します。
     */
    protected ThreadLocal<String> jdbcManagerName = new ThreadLocal<String>();

    /**
     * S2コンテナです。
     */
    protected S2Container container;

    /**
     * @return S2コンテナ
     */
    public S2Container getContainer() {
        return container;
    }

    /**
     * @param container
     *            S2コンテナ
     */
    public void setContainer(S2Container container) {
        this.container = container;
    }

    @Override
    public String getDefaultJdbcManagerName() {
        return defaultJdbcManagerName;
    }

    @Override
    public void setDefaultJdbcManagerName(String name) {
        defaultJdbcManagerName = name;
        selectableJdbcManagerNames.add(name);
    }

    @Override
    public Set<String> getSelectableJdbcManagerNames() {
        return selectableJdbcManagerNames;
    }

    @Override
    public void addSelectableJdbcManagerName(String name) {
        selectableJdbcManagerNames.add(name);
    }

    @Override
    public void removeSelectableJdbcManagerName(String name) {
        selectableJdbcManagerNames.remove(name);
    }

    @Override
    public String getJdbcManagerName() {
        String name = jdbcManagerName.get();
        if (! isValidSelectableJdbcManagerName(name)) {
            name = defaultJdbcManagerName;
        }
        return name;
    }

    @Override
    public void setJdbcManagerName(String name) {
        jdbcManagerName.set(name);
    }

    @Override
    public JdbcManager getJdbcManager() {
        String jdbcManagerName = getJdbcManagerName();
        if (jdbcManagerName == null) {
            throw new EmptyRuntimeException("jdbcManagerName");
        }
        JdbcManager jdbcManager
            = (JdbcManager) container.getRoot().getComponent(
                    getJdbcManagerComponentName(jdbcManagerName));
        return jdbcManager;
    }

    /**
     * JdbcManagerのコンポーネント名を返します。
     *
     * @param name
     *            dao.nameのようなdaoの後ろのサブパッケージ名
     * @return コンポーネント名
     */
    protected String getJdbcManagerComponentName(String name) {
        String jmName = name;
        if (StringUtil.isEmpty(jmName)) {
            return "jdbcManager";
        }
        return jmName + "JdbcManager";
    }

    /**
     * 選択可能な<code>JdbcManager</code>のコンポーネント名か。
     * @param name
     *            <code>JdbcManager</code>のコンポーネント名
     * @return 選択可能な<code>JdbcManager</code>のコンポーネント名の場合 <code>true</code><br>
     *         それ以外の場合 <code>false</code>
     */
    protected boolean isValidSelectableJdbcManagerName(String name) {
        return name != null && getSelectableJdbcManagerNames().contains(name); 
    }
}
