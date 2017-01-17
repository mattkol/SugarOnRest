package com.sugarcrm.pojogen.test;


import com.sugarcrm.pojogen.Main;
import org.junit.Test;

public class GeneratorTests {

    @Test
    public void pojoFromTemplateTest() {

        try {
            Main.main(new String[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pojoFromTemplate2Test() {

        try {
            Main.main(new String[] {"C:\\SugarCRMPojo5"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
