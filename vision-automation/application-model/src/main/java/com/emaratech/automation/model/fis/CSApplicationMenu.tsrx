<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<applicationModel>
    <name>Application Model</name>
    <className>CSApplicationMenu</className>
    <objects>
        <object type="Page" technology="Web">
            <name>VISION-GDRFA</name>
            <codeName>VISIONGDRFAPage</codeName>
            <description></description>
            <identification/>
            <children>
                <object type="WebElement" technology="Web">
                    <name>CS Application</name>
                    <codeName>CSApplicationWebElement</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="InnerText" type="string" isRegExp="false">CS Application</property>
                            <property name="TagName" type="string" isRegExp="false">SPAN</property>
                        </properties>
                    </identification>
                </object>
                <object type="WebElement" technology="Web">
                    <name>Cancel/Closure</name>
                    <codeName>CancelClosureWebElement</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="InnerText" type="string" isRegExp="false">Cancel/Closure</property>
                            <property name="TagName" type="string" isRegExp="false">SPAN</property>
                        </properties>
                    </identification>
                </object>
                <object type="WebElement" technology="Web">
                    <name>Permanent Closure</name>
                    <codeName>PermanentClosureWebElement</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="InnerText" type="string" isRegExp="false">Permanent Closure</property>
                            <property name="TagName" type="string" isRegExp="false">SPAN</property>
                        </properties>
                    </identification>
                </object>
            </children>
        </object>
    </objects>
</applicationModel>
