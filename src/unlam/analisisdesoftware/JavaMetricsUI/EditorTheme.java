package unlam.analisisdesoftware.JavaMetricsUI;

public class EditorTheme {
	private String themeXML = "" +
"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
"<!DOCTYPE RSyntaxTheme SYSTEM \"theme.dtd\">" +

"<RSyntaxTheme version=\"1.0\">" +

"   <background color=\"ffffff\"/>" +
"   <caret color=\"000000\"/>" +
"   <selection bg=\"default\" fg=\"default\"/>" +
"   <currentLineHighlight color=\"e8f2fe\" fade=\"false\"/>" +
"  <marginLine fg=\"b0b4b9\"/> " + 
"  <markAllHighlight color=\"6b8189\"/> <!-- TODO: Fix me -->" +
"  <markOccurrencesHighlight color=\"d4d4d4\" border=\"false\"/>" +
"  <matchedBracket fg=\"c0c0c0\" highlightBoth=\"false\" animate=\"false\"/>" +
"  <hyperlinks fg=\"0000ff\"/>" +
"  <secondaryLanguages>" +
"      <language index=\"1\" bg=\"fff0cc\"/>" +
"      <language index=\"2\" bg=\"dafeda\"/>" +
"      <language index=\"3\" bg=\"ffe0f0\"/>" +
"   </secondaryLanguages>" +
   
"   <!-- Gutter styling. -->" +
"   <gutterBorder color=\"dddddd\"/>" +
"   <lineNumbers fg=\"787878\"/>" +
"   <foldIndicator fg=\"808080\" iconBg=\"ffffff\"/>" +
"   <iconRowHeader activeLineRange=\"3399ff\"/>" +
   
"   <!-- Syntax tokens. -->" +
"   <tokenStyles>" +
"      <style token=\"IDENTIFIER\" fg=\"000000\"/>" +
"      <style token=\"RESERVED_WORD\" fg=\"7f0055\" bold=\"true\"/>" +
"      <style token=\"RESERVED_WORD_2\" fg=\"7f0055\" bold=\"true\"/>" +
"      <style token=\"ANNOTATION\" fg=\"808080\"/>" +
"      <style token=\"COMMENT_DOCUMENTATION\" fg=\"3f5fbf\"/>" +
"      <style token=\"COMMENT_EOL\" fg=\"3f7f5f\"/>" +
"      <style token=\"COMMENT_MULTILINE\" fg=\"3f7f5f\"/>" +
"      <style token=\"COMMENT_KEYWORD\" fg=\"7F9FBF\" bold=\"true\"/>" +
"      <style token=\"COMMENT_MARKUP\" fg=\"7f7f9f\"/>" +
"      <style token=\"DATA_TYPE\" fg=\"7f0055\" bold=\"true\"/>" +
"      <style token=\"FUNCTION\" fg=\"000000\"/>" +
"      <style token=\"LITERAL_BOOLEAN\" fg=\"7f0055\" bold=\"true\"/>" +
"      <style token=\"LITERAL_NUMBER_DECIMAL_INT\" fg=\"000000\"/>" +
"      <style token=\"LITERAL_NUMBER_FLOAT\" fg=\"000000\"/>" +
"      <style token=\"LITERAL_NUMBER_HEXADECIMAL\" fg=\"000000\"/>" +
"      <style token=\"LITERAL_STRING_DOUBLE_QUOTE\" fg=\"2900ff\"/>" +
"      <style token=\"LITERAL_CHAR\" fg=\"2900ff\"/>" +
"      <style token=\"LITERAL_BACKQUOTE\" fg=\"2900ff\"/>" +
"      <style token=\"MARKUP_TAG_DELIMITER\" fg=\"008080\"/>" +
"      <style token=\"MARKUP_TAG_NAME\" fg=\"3f7f7f\"/>" +
"      <style token=\"MARKUP_TAG_ATTRIBUTE\" fg=\"7f007f\"/>" +
"      <style token=\"MARKUP_TAG_ATTRIBUTE_VALUE\" fg=\"2a00ff\" italic=\"true\"/>" +
"      <style token=\"MARKUP_COMMENT\" fg=\"3f5fbf\"/>" +
"      <style token=\"MARKUP_DTD\" fg=\"008080\"/>" +
"      <style token=\"MARKUP_PROCESSING_INSTRUCTION\" fg=\"808080\"/>" +
"      <style token=\"MARKUP_CDATA\" fg=\"000000\"/>" +
"      <style token=\"MARKUP_CDATA_DELIMITER\" fg=\"008080\"/>" +
"      <style token=\"MARKUP_ENTITY_REFERENCE\" fg=\"2a00ff\"/>" +
"      <style token=\"OPERATOR\" fg=\"000000\"/>" +
"      <style token=\"PREPROCESSOR\" fg=\"808080\"/>" +
"      <style token=\"REGEX\" fg=\"008040\"/>" +
"      <style token=\"SEPARATOR\" fg=\"000000\"/>" +
"      <style token=\"VARIABLE\" fg=\"ff9900\" bold=\"true\"/>" +
"      <style token=\"WHITESPACE\" fg=\"000000\"/>" +
      
"      <style token=\"ERROR_IDENTIFIER\" fg=\"000000\" bg=\"ffcccc\"/>" +
"      <style token=\"ERROR_NUMBER_FORMAT\" fg=\"000000\" bg=\"ffcccc\"/>" +
"      <style token=\"ERROR_STRING_DOUBLE\" fg=\"000000\" bg=\"ffcccc\"/>" +
"      <style token=\"ERROR_CHAR\" fg=\"000000\" bg=\"ffcccc\"/>" +
"   </tokenStyles>" +

"</RSyntaxTheme>";

	public String getThemeXML() {
		return themeXML;
	}
}
