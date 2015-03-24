<?xml version="1.0" encoding="UTF-8"?>
<module rename-to='emistoolbox'>
  <!-- Inherit the core Web Toolkit stuff.                        -->
  <inherits name='com.google.gwt.user.User'/>

  <!-- Inherit the GWT Internationalization stuff.                        -->
  <inherits name="com.google.gwt.i18n.I18N"/>

  <!-- Inherit the default GWT style sheet.  You can change       -->
  <!-- the theme of your GWT application by uncommenting          -->
  <!-- any one of the following lines.                            -->
  <!--  <inherits name='com.google.gwt.user.theme.standard.Standard'/> -->
  <!-- <inherits name='com.google.gwt.user.theme.chrome.Chrome'/> -->
  <!-- <inherits name='com.google.gwt.user.theme.dark.Dark'/>     -->

  <!-- Other module inherits                                      -->
  <inherits name='com.emistoolbox.common'/>
<!--  <set-property name="user.agent" value="gecko"/> -->

  <!-- Specify the app entry point class.                         -->
  <entry-point class='com.emistoolbox.client.admin.EmisToolbox'/>

  <!-- Specify the paths for translatable code                    -->
  <source path='client'/>

</module>
