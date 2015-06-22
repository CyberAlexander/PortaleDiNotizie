<%--
  Created by IntelliJ IDEA.
  User: alexanderleonovich
  Date: 08.05.15
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="commentary" class="by.leonovich.notizieportale.domain.Commentary" scope="session"/>
<html>
<head>
    <title></title>
</head>
<body>
<div>
    <%-- COMMENT-CONTENT FOR NEWS, WHAT ROLE_USER WATCH NOW --%>
    <c:if test="${commentaries[0] != null}">
        <h5 class="most-popular-news-header" style="text-align: right">Commentaries: </h5>
    </c:if>
    <c:forEach items="${commentaries}" var="commentary">
        <hr/>
        <div align="right">
            <em>| date: <fmt:formatDate pattern="dd-MMM-yyyy" value="${commentary.date}"/></em><em> |
            user: ${commentary.person.name} |</em>
        </div>
        <p class="text">${commentary.comment}</p>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <table>
                <tr>
                    <th>
                        <form method="post" action="edit_commentary.do">
                            <input type="hidden" name="commentaryId" value="${commentary.commentaryId}">
                            <button type="submit" class="btn btn-default">edit commentary</button>
                        </form>
                    </th>
                    <th>
                        <form method="post" action="deletecommentary.do">
                            <input type="hidden" name="commentaryId" value="${commentary.commentaryId}">
                            <button type="submit" class="btn btn-danger">delete commentary</button>
                        </form>
                    </th>
                </tr>
            </table>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_USER')">
            <c:if test="${person.personId == commentObj.person.personId}">
                <table>
                    <tr>
                        <th>
                            <form method="post" action="edit_commentary.do">
                                <input type="hidden" name="commentaryId" value="${commentary.commentaryId}">
                                <button type="submit" class="btn btn-default">edit comment</button>
                            </form>
                        </th>
                        <th>
                            <form method="post" action="deletecommentary.do">
                                <input type="hidden" name="commentaryId" value="${commentary.commentaryId}">
                                <button type="submit" class="btn btn-danger">delete comment</button>
                            </form>
                        </th>
                    </tr>
                </table>
        </c:if>
        </sec:authorize>
    </c:forEach>
    <hr/>
</div>
</body>
</html>
