<%--
  Created by IntelliJ IDEA.
  User: luuki
  Date: 02/04/19
  Time: 5:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Task list</title>
    <link rel="stylesheet" type="text/css" href="resources/styles/tasks.css"	media="screen" />
    <script src="resources/scripts/jquery-2.0.3.js"></script>
    <script src="resources/scripts/jquery-tmpl.js"></script>
    <script src="resources/scripts/jquery.validate.js"></script>
    <script src="resources/scripts/jquery-serialization.js"></script>
    <script src="resources/scripts/tasks-controller.js"></script>
    <script src="resources/scripts/date.js"></script>
</head>
<body>
<header>
    <span>Task list</span>
</header>
<main  id="taskPage">
    <section id="taskCreation" class="not">
        <form id="taskForm">
            <input type="hidden" name="id"/>
            <div>
                <label>Task</label> <input type="text" required="required"
                                           name="task" class="large" placeholder="Breakfast at Tiffanys" maxlength="200"  />
            </div>
            <div>
                <label>Required by</label> <input type="date" required="required"
                                                  name="requiredBy" />
            </div>
            <div>
                <label>Category</label> <select name="category">
                <option value="Personal">Personal</option>
                <option value="Work">Work</option>
            </select>
            </div>
            <nav>
                <a href="#" id="saveTask">Save task</a>    <!-- https://stackoverflow.com/questions/4855168/what-is-href-and-why-is-it-used -->
                <a href="#" id="clearTask">Clear task</a>
            </nav>
        </form>
    </section>
    <section>
        <table id="tblTasks">
            <colgroup>
                <col width="20%"> <!--Name-->
                <col width="10%"> <!--Due-->
                <col width="10%"> <!--Category-->
                <col width="10%"> <!--Priority-->
                <col width="10%"> <!--Assign To-->
                <col width="10%"> <!--Created By-->
                <col width="10%"> <!--Status-->
                <col width="20%"> <!--Action-->
            </colgroup>
            <thead>
            <tr>
                <th>Name</th>
                <th>Due</th>
                <th>Category</th>
                <th>Priority</th>
                <th>Assign To</th>
                <th>Created By</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <nav>
            <a href="#" id="btnAddTask">Add task</a>
            <a href="#" id="btnRetrieveTasks">Retrieve tasks from server</a>
        </nav>
    </section>
</main>
<footer>You have <span id="taskCount"></span> tasks</footer>
</body>
<script>
    function initScreen() {
        $(document).ready(function() {
            tasksController.init($('#taskPage'), function() {
                tasksController.loadTasks();
            });
        });
    }
    if (window.indexedDB) {
        console.log("using indexedDB 111917kl");
        $.getScript( "resources/scripts/tasks-indexeddb.js" )
            .done(function( script, textStatus ) {
                initScreen();
            })
            .fail(function( jqxhr, settings, exception ) {
                console.log( 'Failed to load indexed db script' );
            });
    } else if (window.localStorage) {
        console.log("using webstorage 111917kl");
        $.getScript( "resources/scripts/tasks-webstorage.js" )
            .done(function( script, textStatus ) {
                initScreen();
            })
            .fail(function( jqxhr, settings, exception ) {
                console.log( 'Failed to load web storage script' );
            });
    }
</script>

<script id="taskRow" type="text/x-jQuery-tmpl">
<tr>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= task}}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= dueDate}}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= category}}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= priority}}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= assignUser}}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= createUser}}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= status}}</td>
	<td>
		<nav>
			{{if complete != true}}
				<a href="#" class="editRow" data-task-id="{{= id}}">Edit</a>
				<a href="#" class="completeRow" data-task-id="{{= id}}">Complete</a>
			{{/if}}
			<a href="#" class="deleteRow" data-task-id="{{= id}}">Delete</a>
		</nav>
	</td>
</tr>
</script>


</html>

