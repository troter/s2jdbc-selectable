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

import java.util.List;

import javax.sql.DataSource;

import jp.troter.seasar.extension.jdbc.SelectableJdbcManagerFactory;

import org.seasar.extension.jdbc.AutoBatchDelete;
import org.seasar.extension.jdbc.AutoBatchInsert;
import org.seasar.extension.jdbc.AutoBatchUpdate;
import org.seasar.extension.jdbc.AutoDelete;
import org.seasar.extension.jdbc.AutoFunctionCall;
import org.seasar.extension.jdbc.AutoInsert;
import org.seasar.extension.jdbc.AutoProcedureCall;
import org.seasar.extension.jdbc.AutoSelect;
import org.seasar.extension.jdbc.AutoUpdate;
import org.seasar.extension.jdbc.DbmsDialect;
import org.seasar.extension.jdbc.EntityMetaFactory;
import org.seasar.extension.jdbc.JdbcContext;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.SqlBatchUpdate;
import org.seasar.extension.jdbc.SqlFileBatchUpdate;
import org.seasar.extension.jdbc.SqlFileFunctionCall;
import org.seasar.extension.jdbc.SqlFileProcedureCall;
import org.seasar.extension.jdbc.SqlFileSelect;
import org.seasar.extension.jdbc.SqlFileUpdate;
import org.seasar.extension.jdbc.SqlFunctionCall;
import org.seasar.extension.jdbc.SqlProcedureCall;
import org.seasar.extension.jdbc.SqlSelect;
import org.seasar.extension.jdbc.SqlUpdate;
import org.seasar.extension.jdbc.manager.JdbcManagerImplementor;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.PersistenceConvention;

public class SelectableJdbcManagerProxy implements JdbcManager, JdbcManagerImplementor {

    /**
     * S2コンテナです。
     */
    protected S2Container container;

    /**
     * <code>SelectableJdbcManagerFactory</code>のコンポーネント名です。
     */
    protected String selectableJdbcManagerFactoryName;

    /**
     * <code>SelectableJdbcManagerFactory</code>のコンポーネント名を設定します。
     * @param name
     */
    public void setMasterSlaveJdbcManagerFactoryName(String name) {
        this.selectableJdbcManagerFactoryName = name;
    }

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

    /**
     * 選択可能な<code>JdbcManager</code>のファクトリを取得します。
     */
    protected SelectableJdbcManagerFactory getSelectableJdbcManagerFactory() {
        return (SelectableJdbcManagerFactory)container.getRoot().getComponent(selectableJdbcManagerFactoryName);
    }

    /**
     * <code>JdbcManager</code>を取得します。
     * @return <code>JdbcManager</code>
     */
    protected JdbcManager getJdbcManager() {
        return getSelectableJdbcManagerFactory().getJdbcManager();
    }

    @Override
    public <T> AutoSelect<T> from(Class<T> baseClass) {
        return getJdbcManager().from(baseClass);
    }

    @Override
    public <T> SqlSelect<T> selectBySql(Class<T> baseClass, String sql,
            Object... params) {
        return getJdbcManager().selectBySql(baseClass, sql, params);
    }

    @Override
    public long getCountBySql(String sql, Object... params) {
        return getJdbcManager().getCountBySql(sql, params);
    }

    @Override
    public <T> SqlFileSelect<T> selectBySqlFile(Class<T> baseClass, String path) {
        return getJdbcManager().selectBySqlFile(baseClass, path);
    }

    @Override
    public <T> SqlFileSelect<T> selectBySqlFile(Class<T> baseClass,
            String path, Object parameter) {
        return getJdbcManager().selectBySqlFile(baseClass, path, parameter);
    }

    @Override
    public long getCountBySqlFile(String path) {
        return getJdbcManager().getCountBySqlFile(path);
    }

    @Override
    public long getCountBySqlFile(String path, Object parameter) {
        return getJdbcManager().getCountBySqlFile(path, parameter);
    }

    @Override
    public <T> AutoInsert<T> insert(T entity) {
        return getJdbcManager().insert(entity);
    }

    @Override
    public <T> AutoBatchInsert<T> insertBatch(T... entities) {
        return getJdbcManager().insertBatch(entities);
    }

    @Override
    public <T> AutoBatchInsert<T> insertBatch(List<T> entities) {
        return getJdbcManager().insertBatch(entities);
    }

    @Override
    public <T> AutoUpdate<T> update(T entity) {
        return getJdbcManager().update(entity);
    }

    @Override
    public <T> AutoBatchUpdate<T> updateBatch(T... entities) {
        return getJdbcManager().updateBatch(entities);
    }

    @Override
    public <T> AutoBatchUpdate<T> updateBatch(List<T> entities) {
        return getJdbcManager().updateBatch(entities);
    }

    @Override
    public SqlUpdate updateBySql(String sql, Class<?>... paramClasses) {
        return getJdbcManager().updateBySql(sql, paramClasses);
    }

    @Override
    public SqlBatchUpdate updateBatchBySql(String sql, Class<?>... paramClasses) {
        return getJdbcManager().updateBatchBySql(sql, paramClasses);
    }

    @Override
    public SqlFileUpdate updateBySqlFile(String path) {
        return getJdbcManager().updateBySqlFile(path);
    }

    @Override
    public SqlFileUpdate updateBySqlFile(String path, Object parameter) {
        return getJdbcManager().updateBySqlFile(path, parameter);
    }

    @Override
    public <T> SqlFileBatchUpdate<T> updateBatchBySqlFile(String path,
            T... params) {
        return getJdbcManager().updateBatchBySqlFile(path, params);
    }

    @Override
    public <T> SqlFileBatchUpdate<T> updateBatchBySqlFile(String path,
            List<T> params) {
        return getJdbcManager().updateBatchBySqlFile(path, params);
    }

    @Override
    public <T> AutoDelete<T> delete(T entity) {
        return getJdbcManager().delete(entity);
    }

    @Override
    public <T> AutoBatchDelete<T> deleteBatch(T... entities) {
        return getJdbcManager().deleteBatch(entities);
    }

    @Override
    public <T> AutoBatchDelete<T> deleteBatch(List<T> entities) {
        return getJdbcManager().deleteBatch(entities);
    }

    @Override
    public AutoProcedureCall call(String procedureName) {
        return getJdbcManager().call(procedureName);
    }

    @Override
    public AutoProcedureCall call(String procedureName, Object parameter) {
        return getJdbcManager().call(procedureName, parameter);
    }

    @Override
    public SqlProcedureCall callBySql(String sql) {
        return getJdbcManager().callBySql(sql);
    }

    @Override
    public SqlProcedureCall callBySql(String sql, Object parameter) {
        return getJdbcManager().callBySql(sql, parameter);
    }

    @Override
    public SqlFileProcedureCall callBySqlFile(String path) {
        return getJdbcManager().callBySqlFile(path);
    }

    @Override
    public SqlFileProcedureCall callBySqlFile(String path, Object parameter) {
        return getJdbcManager().callBySqlFile(path, parameter);
    }

    @Override
    public <T> AutoFunctionCall<T> call(Class<T> resultClass,
            String functionName) {
        return getJdbcManager().call(resultClass, functionName);
    }

    @Override
    public <T> AutoFunctionCall<T> call(Class<T> resultClass,
            String functionName, Object parameter) {
        return getJdbcManager().call(resultClass, functionName, parameter);
    }

    @Override
    public <T> SqlFunctionCall<T> callBySql(Class<T> resultClass, String sql) {
        return getJdbcManager().callBySql(resultClass, sql);
    }

    @Override
    public <T> SqlFunctionCall<T> callBySql(Class<T> resultClass, String sql,
            Object parameter) {
        return getJdbcManager().callBySql(resultClass, sql, parameter);
    }

    @Override
    public <T> SqlFileFunctionCall<T> callBySqlFile(Class<T> resultClass,
            String path) {
        return getJdbcManager().callBySqlFile(resultClass, path);
    }

    @Override
    public <T> SqlFileFunctionCall<T> callBySqlFile(Class<T> resultClass,
            String path, Object parameter) {
        return getJdbcManager().callBySqlFile(resultClass, path, parameter);
    }

    @Override
    public JdbcContext getJdbcContext() {
        return Methods.implementor(getJdbcManager()).getJdbcContext();
    }

    @Override
    public DataSource getDataSource() {
        return Methods.implementor(getJdbcManager()).getDataSource();
    }

    @Override
    public String getSelectableDataSourceName() {
        return Methods.implementor(getJdbcManager()).getSelectableDataSourceName();
    }

    @Override
    public DbmsDialect getDialect() {
        return Methods.implementor(getJdbcManager()).getDialect();
    }

    @Override
    public EntityMetaFactory getEntityMetaFactory() {
        return Methods.implementor(getJdbcManager()).getEntityMetaFactory();
    }

    @Override
    public PersistenceConvention getPersistenceConvention() {
        return Methods.implementor(getJdbcManager()).getPersistenceConvention();
    }

    @Override
    public boolean isAllowVariableSqlForBatchUpdate() {
        return Methods.implementor(getJdbcManager()).isAllowVariableSqlForBatchUpdate();
    }

    public static class Methods {
        public static JdbcManagerImplementor implementor(JdbcManager jdbcManager) {
            if (jdbcManager instanceof JdbcManagerImplementor) {
                return ((JdbcManagerImplementor)jdbcManager);
            }
            throw new UnsupportedOperationException();
        }
    }
}
