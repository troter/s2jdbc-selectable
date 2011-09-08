シャーディング用のS2JDBCのJdbcManager
=====================================

pom.xmlに追記する設定
---------------------

**mavenリポジトリを追加**

    <repositories>
      <repository>
        <id>troter.jp snapshot</id>
        <name>TROTER.JP Snapshot Maven2 Repository</name>
        <url>http://troter.jp/maven2/snapshot</url>
      </repository>
    </repositories>

**依存関係を追加**

    <dependencies>
      <dependency>
        <groupId>jp.troter</groupId>
        <artifactId>s2jdbc-selectable</artifactId>
        <version>1.0.0-SNAPSHOT</version>
      </dependency>
    </dependencies>

利用設定
--------

**s2jdbc.diconの設定例**

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE components PUBLIC "-//SEASAR//DTD S2Container 2.4//EN"
            "http://www.seasar.org/dtd/components24.dtd">
    <components>
        <include path="s2jdbc-node1.dicon"/><!-- node1JdbcManagerの登録を行う -->
        <include path="s2jdbc-node2.dicon"/><!-- node2JdbcManagerの登録を行う -->
    
        <!-- AbstractServiceではこのjdbcManagerを利用する -->
        <component name="jdbcManager"
          class="jp.troter.seasar.extension.jdbc.manager.SelectableJdbcManagerProxy">
          <property name="selectableJdbcManagerFactoryName">"selectableJdbcManagerFactory"</property>
        </component>
    
        <!-- ファクトリ。
         | ノードの切り替えなどを担当
         | SelectableJdbcManagerFactory#setJdbcManagerName(String)を利用してノードを切り替える
         |-->
        <component name="selectableJdbcManagerFactory"
          class="jp.troter.seasar.extension.jdbc.manager.SelectableJdbcManagerFactoryImpl">
          <property name="defaultJdbcManagerName">"node1"</property> <!-- 便宜上、node1をデフォルトに -->
          <initMethod name="addSelectableJdbcManagerName"><arg>"node2"</arg></initMethod>
        </component>
    </components>
