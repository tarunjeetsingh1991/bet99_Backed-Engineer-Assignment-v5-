<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Bet99 Report</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { cursor: pointer; }
        .pagination { margin-top: 10px; }
        .pagination a { margin: 0 5px; text-decoration: none; }
        .summary-box {
            border: 1px solid #ccc;
            padding: 10px;
            width: 300px;
            margin: 15px 0;
            background: #f9f9f9;
        }
    </style>
</head>

<body>

<h2>Generate Report</h2>

<form method="get" action="report">

    Start: <input type="datetime-local" name="startDate" required value="${startDate}">
    End: <input type="datetime-local" name="endDate" required value="${endDate}">
    <br/><br/>

    Account: <input type="text" name="accountId" value="${accountId}">
    Type: <input type="text" name="tranType" value="${tranType}">
    Game ID: <input type="text" name="gameId" value="${gameId}">
    <br/><br/>

    Platform Tran ID: <input type="text" name="platformTranId" value="${platformTranId}">
    Game Tran ID: <input type="text" name="gameTranId" value="${gameTranId}">
    <br/><br/>

    Sort By:
    <select name="sortBy">
        <option value="datetime" ${sortBy == 'datetime' ? 'selected' : ''}>Date</option>
        <option value="accountId" ${sortBy == 'accountId' ? 'selected' : ''}>Account</option>
        <option value="tranType" ${sortBy == 'tranType' ? 'selected' : ''}>Type</option>
        <option value="gameId" ${sortBy == 'gameId' ? 'selected' : ''}>Game ID</option>
    </select>

    Direction:
    <select name="direction">
        <option value="asc" ${direction == 'asc' ? 'selected' : ''}>ASC</option>
        <option value="desc" ${direction == 'desc' ? 'selected' : ''}>DESC</option>
    </select>

    <br/><br/>

    <button type="submit">Search</button>

    <button type="submit"
            formaction="report/csv"
            formmethod="get">
        Export CSV
    </button>

</form>

<hr/>

Total Records: ${totalRecords}

<!-- ===== SUMMARY SECTION ===== -->
<c:if test="${summary != null}">
    <div class="summary-box">
        <h3>Summary</h3>
        Total Bet: ${summary.totalBet} <br/>
        Total Win: ${summary.totalWin} <br/>
        Net: ${summary.net}
    </div>
</c:if>

<!-- ===== REPORT TABLE ===== -->
<table>
<tr>
    <th>ID</th>
    <th>Account</th>
    <th>Date</th>
    <th>Type</th>
    <th>Platform Tran</th>
    <th>Game Tran</th>
    <th>Game ID</th>
    <th>Amount</th>
    <th>Balance</th>
</tr>

<c:forEach var="r" items="${reportList}">
<tr>
    <td>${r.id}</td>
    <td>${r.accountId}</td>
    <td>${r.dateTime}</td>
    <td>${r.tranType}</td>
    <td>${r.platformTranId}</td>
    <td>${r.gameTranId}</td>
    <td>${r.gameId}</td>
    <td>${r.amount}</td>
    <td>${r.balance}</td>
</tr>
</c:forEach>
</table>

<!-- ===== PAGINATION ===== -->
<div class="pagination">

    <c:if test="${currentPage > 1}">
        <a href="report?page=${currentPage - 1}&startDate=${startDate}&endDate=${endDate}&accountId=${accountId}&tranType=${tranType}&gameId=${gameId}&platformTranId=${platformTranId}&gameTranId=${gameTranId}&sortBy=${sortBy}&direction=${direction}">
            Previous
        </a>
    </c:if>

    Page ${currentPage} of ${totalPages}

    <c:if test="${currentPage < totalPages}">
        <a href="report?page=${currentPage + 1}&startDate=${startDate}&endDate=${endDate}&accountId=${accountId}&tranType=${tranType}&gameId=${gameId}&platformTranId=${platformTranId}&gameTranId=${gameTranId}&sortBy=${sortBy}&direction=${direction}">
            Next
        </a>
    </c:if>

</div>

</body>
</html>