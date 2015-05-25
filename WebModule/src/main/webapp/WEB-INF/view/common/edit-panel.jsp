<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexanderleonovich
  Date: 25.04.15
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<table>
    <caption>EDIT PANEL</caption>
    <tr>
        <th>
            <%-- BUTTON FOR EDIT NEWS-PAGE OR FOR EDIT CATEGORY. IF TALKING SIMPLE, IT`S BUTTON FOR EDIT PAGE, WHERE USER IS LOCATED NOW  --%>
            <form method="post" action="controller">
                <input type="hidden" name="command" value="editnews"/>
                <input type="hidden" name="newsIdForEdit" value="${news.newsId}">
                <button type="submit" class="btn btn-info">edit ${news.title}</button>
            </form>
        </th>
        <th>
            <%-- BUTTON FOR DELETE NEWS-PAGE --%>
                    <c:if test="${news.category.categoryId != 1}">
                        <form name="delete_news_form" method="post" action="controller">
                            <input type="hidden" name="command" value="deletenews"/>
                            <input type="hidden" name="newsIdForDelete" value="${news.newsId}">
                            <button type="submit" class="btn btn-danger">DELETE ${news.title} !!!</button>
                        </form>
                    </c:if>
        </th>
        <th>
            <%-- BUTTON FOR ADDING NEWS IN CATEGORY OR FOR ADDING NEW CATEGORY --%>
            <form method="post" action="controller">
                <input type="hidden" name="command" value="addnews"/>

                <c:if test="${newses[0] != null}">
                    <c:set var="category" scope="session" value="${newses[0].category.categoryName}"/>
                    <c:set var="newsList" scope="session" value="${newses}"/>
                </c:if>
                <c:if test="${newses[0] == null}">
                    <c:set var="category" scope="session" value="${news.pageId}"/>
                </c:if>

                <c:if test="${news.pageId eq 'main'}">
                    <button type="submit"  class="btn btn-success">Add new category</button>
                </c:if>
                <c:if test="${news.category.categoryName eq 'main' && news.newsId != 1}">
                    <button type="submit"  class="btn btn-success">Add new ${news.pageId} news</button>
                </c:if>
                <c:if test="${news.category.categoryName != 'main' && news.pageId != 'main'}">
                    <button type="submit"  class="btn btn-success">Add new ${news.category.categoryName} news</button>
                </c:if>

            </form>
        </th>
    </tr>
</table>
</body>
</html>
