<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<applicationModel>
    <name>Login Model</name>
    <className>LoginModel</className>
    <objects>
        <object type="Page" technology="Web">
            <name>localhost:8080/visioncore/index.jsp</name>
            <codeName>Localhost8080VisioncoreIndexJspPage</codeName>
            <description></description>
            <identification/>
            <children>
                <object type="Button" technology="Web">
                    <name>Launch Application</name>
                    <codeName>LaunchApplicationButton</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="ButtonType" type="string" isRegExp="false">button</property>
                            <property name="Name" type="string" isRegExp="false">Launch Application</property>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                        </properties>
                    </identification>
                </object>
            </children>
        </object>
        <object type="Page" technology="Web">
            <name>VISION-GDRFA</name>
            <codeName>VISIONGDRFAPage</codeName>
            <description></description>
            <identification/>
            <children>
                <object type="EditField" technology="Web">
                    <name>j_username</name>
                    <codeName>JUsernameEditField</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="Name" type="string" isRegExp="false">j_username</property>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                            <property name="Type" type="string" isRegExp="false">text</property>
                        </properties>
                    </identification>
                </object>
                <object type="EditField" technology="Web">
                    <name>j_password</name>
                    <codeName>JPasswordEditField</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="Name" type="string" isRegExp="false">j_password</property>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                            <property name="Type" type="string" isRegExp="false">password</property>
                        </properties>
                    </identification>
                </object>
                <object type="Link" technology="Web">
                    <name>LOGIN </name>
                    <codeName>LOGINLink</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="InnerText" type="string" isRegExp="false">LOGIN </property>
                            <property name="TagName" type="string" isRegExp="false">A</property>
                        </properties>
                    </identification>
                </object>
            </children>
        </object>
    </objects>
</applicationModel>
