<%
String json = (String)request.getAttribute("jsonString");
if(json!=null){
	out.append(json.replace("\r", "\\r").replace("\n", "\\n"));
}
%>