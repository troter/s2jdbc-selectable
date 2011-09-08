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
package jp.troter.seasar.extension.jdbc;

import java.util.Set;

import org.seasar.extension.jdbc.JdbcManager;

public interface SelectableJdbcManagerFactory {

    /**
     * デフォルトの<code>JdbcManager</code>のコンポーネント名を取得します。
     * @return デフォルトの<code>JdbcManager</code>のコンポーネント名
     */
    String getDefaultJdbcManagerName();

    /**
     * デフォルトの<code>JdbcManager</code>のコンポーネント名を設定します。
     * @param name デフォルトの<code>JdbcManager</code>のコンポーネント名
     */
    void setDefaultJdbcManagerName(String name);

    /**
     * 選択可能な<code>JdbcManager</code>のコンポーネント名一覧を追加します。
     * @return
     */
    Set<String> getSelectableJdbcManagerNames();

    /**
     * 選択可能な<code>JdbcManager</code>のコンポーネント名を追加します。
     * @param name 選択可能な<code>JdbcManager</code>のコンポーネント名
     */
    void addSelectableJdbcManagerName(String name);

    /**
     * 選択可能な<code>JdbcManager</code>のコンポーネント名を削除します。
     * @param name 選択可能な<code>JdbcManager</code>のコンポーネント名
     */
    void removeSelectableJdbcManagerName(String name);

    /**
     * 現在選択されている<code>JdbcManager</code>のコンポーネント名を取得します。
     * @return 現在選択されている<code>JdbcManager</code>のコンポーネント名
     */
    String getJdbcManagerName();

    /**
     * 利用する<code>JdbcManager</code>のコンポーネント名を設定します。
     * @param name 利用する<code>JdbcManager</code>のコンポーネント名
     */
    void setJdbcManagerName(String name);

    /**
     * 現在選択されている<code>JdbcManager</code>を取得します。
     * @return
     */
    JdbcManager getJdbcManager();
}
