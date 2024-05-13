package meuapp.config;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class OutputStyles {
    public void ApplyStyles(HTMLEditorKit editor) {
        StyleSheet styleSheet = editor.getStyleSheet();
        styleSheet.addRule("body {font-family: arial, sans-serif; padding: 3px;}");
        styleSheet.addRule("table {\r\n" +
                "  border-collapse: collapse;\r\n" +
                "  width: 100%;\r\n" +
                "   }");
        styleSheet.addRule("td, th {\r\n" +
                "  border: 1px solid #dddddd;\r\n" +
                "  text-align: left;\r\n" +
                "  padding: 8px;\r\n" +
                "}");
        styleSheet.addRule("tr:nth-child(even) {\r\n" +
                "  background-color: #dddddd;\r\n" +
                "}");

    }

}
