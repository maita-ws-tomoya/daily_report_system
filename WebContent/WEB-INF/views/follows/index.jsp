<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>フォロー従業員　一覧</h2>
        <table id="follow_employee_list">

            <c:choose>
            <c:when test = "${login_employee.admin_flag == 1}">

                <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>詳細</th>
                    <th>日報</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${follow.code}" /></td>
                        <td><c:out value="${follow.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${follow.delete_flag == 1}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${follow.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="<c:url value='/reports/follow/index?id=${follow.id}' />">日報一覧</a></td>
                    </tr>
                </c:forEach>
            </tbody>

            </c:when>

            <c:otherwise>
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>日報</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${follow.code}" /></td>
                        <td><c:out value="${follow.name}" /></td>
                        <td><a href="<c:url value='/reports/follow/index?id=${follow.id}' />">日報一覧</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </c:otherwise>
            </c:choose>

        </table>

        <div id="pagination">
            （全 ${follows_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((follows_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/follows/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>


    </c:param>
</c:import>