<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String rid = request.getParameter("resourceId");
    if (rid == null) rid = "";
%>
<!DOCTYPE html>
<html>
<head>
    <title>Book Resource</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width: 820px;">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="mb-0">Book Resource</h2>
        <div class="d-flex gap-2">
            <a class="btn btn-outline-secondary" href="resources.jsp">Resources</a>
            <a class="btn btn-outline-danger" href="logout.jsp">Logout</a>
        </div>
    </div>

    <div class="card shadow-sm rounded-4">
        <div class="card-body">
            <div id="msg"></div>

            <div class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Resource ID</label>
                    <input id="resourceId" class="form-control" value="<%= rid %>">
                </div>
                <div class="col-md-8">
                    <label class="form-label">Purpose</label>
                    <input id="purpose" class="form-control" placeholder="Group study / Lab work">
                </div>
                <div class="col-md-6">
                    <label class="form-label">Start (YYYY-MM-DDTHH:MM)</label>
                    <input id="startTime" class="form-control" placeholder="2026-02-24T10:00">
                </div>
                <div class="col-md-6">
                    <label class="form-label">End (YYYY-MM-DDTHH:MM)</label>
                    <input id="endTime" class="form-control" placeholder="2026-02-24T11:00">
                </div>
            </div>

            <div class="d-flex gap-2 mt-4">
                <button class="btn btn-outline-secondary" onclick="checkAvail()">Check Availability</button>
                <button class="btn btn-primary" onclick="book()">Book</button>
            </div>
        </div>
    </div>
</div>

<script>
    const base = "<%=request.getContextPath()%>";
    const token = localStorage.getItem("token");
    if(!token) window.location.href="login.jsp";

    function show(type, text){
        document.getElementById("msg").innerHTML =
            "<div class='alert alert-" + type + " rounded-4'>" + text + "</div>";
    }

    function v(id){ return document.getElementById(id).value.trim(); }

    async function checkAvail(){
        const rid = v("resourceId");
        const start = v("startTime");
        const end = v("endTime");
        const url = base + "/api/availability?resourceId=" + encodeURIComponent(rid)
            + "&start=" + encodeURIComponent(start)
            + "&end=" + encodeURIComponent(end);

        const res = await fetch(url, { headers: {"Authorization":"Bearer " + token}});
        const data = await res.json();
        if(data.available) show("success","Available ✅");
        else show("danger","Not available ❌");
    }

    async function book(){
        const rid = v("resourceId");
        const payload = { startTime: v("startTime"), endTime: v("endTime"), purpose: v("purpose") };

        const res = await fetch(base + "/api/reservations?resourceId=" + encodeURIComponent(rid), {
            method:"POST",
            headers: {"Content-Type":"application/json", "Authorization":"Bearer " + token},
            body: JSON.stringify(payload)
        });

        const text = await res.text();
        if(res.status===201){ show("success","Booked ✅"); }
        else if(res.status===409){ show("danger","Conflict ❌ " + text); }
        else show("danger","Error ("+res.status+"): " + text);
    }
</script>
</body>
</html>