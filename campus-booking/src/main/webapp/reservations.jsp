<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reservations</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="mb-0">Reservations</h2>
        <div class="d-flex gap-2">
            <a class="btn btn-outline-secondary" href="resources.jsp">Resources</a>
            <a class="btn btn-outline-danger" href="logout.jsp">Logout</a>
        </div>
    </div>

    <div class="card shadow-sm rounded-4">
        <div class="table-responsive">
            <table class="table table-hover mb-0">
                <thead>
                <tr>
                    <th>ID</th><th>Resource</th><th>Booked By</th><th>Start</th><th>End</th><th>Status</th><th></th>
                </tr>
                </thead>
                <tbody id="rows"></tbody>
            </table>
        </div>
    </div>
</div>

<script>
    const base = "<%=request.getContextPath()%>";
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");
    if(!token) window.location.href="login.jsp";

    function esc(s){
        if (s === null || s === undefined) return "";
        return String(s).replaceAll("&","&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;");
    }

    async function load(){
        const rows = document.getElementById("rows");
        rows.innerHTML = "<tr><td colspan='7' class='text-muted'>Loading...</td></tr>";

        const res = await fetch(base + "/api/reservations", { headers: {"Authorization":"Bearer " + token}});
        if(!res.ok){ rows.innerHTML="<tr><td colspan='7' class='text-danger'>Failed ("+res.status+")</td></tr>"; return; }

        const data = await res.json();
        if(data.length===0){ rows.innerHTML="<tr><td colspan='7' class='text-muted'>No reservations</td></tr>"; return; }

        rows.innerHTML="";
        data.forEach(x=>{
            const btn = (x.status === "BOOKED")
                ? "<button class='btn btn-sm btn-outline-danger' onclick='cancelRes(" + x.id + ")'>Cancel</button>"
                : "";
            const tr = document.createElement("tr");
            tr.innerHTML =
                "<td>"+esc(x.id)+"</td>" +
                "<td>"+esc(x.resourceName)+"</td>" +
                "<td>"+esc(x.bookedBy)+"</td>" +
                "<td>"+esc(x.startTime)+"</td>" +
                "<td>"+esc(x.endTime)+"</td>" +
                "<td><span class='badge "+(x.status==="BOOKED"?"bg-success":"bg-secondary")+"'>"+esc(x.status)+"</span></td>" +
                "<td>"+btn+"</td>";
            rows.appendChild(tr);
        });
    }

    async function cancelRes(id){
        const res = await fetch(base + "/api/reservations/" + encodeURIComponent(id) + "/cancel", {
            method:"POST",
            headers: {"Authorization":"Bearer " + token}
        });
        const text = await res.text();
        if(res.status===200) load();
        else alert("Cancel failed ("+res.status+"): " + text);
    }

    load();
</script>
</body>
</html>