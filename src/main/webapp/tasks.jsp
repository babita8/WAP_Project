<%--
  Created by IntelliJ IDEA.
  User: luuki
  Date: 02/04/19
  Time: 5:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


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
    <script src="resources/scripts/tableSort.js"></script>
    <script src="resources/scripts/jquery.star-rating-svg.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .checked {
            color: orange;
        }
    </style>

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
                <!-- Rating Stars Box -->
            <div>
            <label>Priority</label>
                <div class='rating-stars text-center'>
                    <ul id='stars'>
                        <li class='star' title='Lowest ' data-value='1'>
                            <i class='fa fa-star fa-fw'></i>
                        </li>
                        <li class='star' title='Low' data-value='2'>
                            <i class='fa fa-star fa-fw'></i>
                        </li>
                        <li class='star' title='Normal' data-value='3'>
                            <i class='fa fa-star fa-fw'></i>
                        </li>
                        <li class='star' title='High' data-value='4'>
                            <i class='fa fa-star fa-fw'></i>
                        </li>
                        <li class='star' title='Highest!!!' data-value='5'>
                            <i class='fa fa-star fa-fw'></i>
                        </li>
                    </ul>
                </div>
            </div>

            <div>
                <label>Assigned to</label>
                <select name="assigned">
                    <option value="${user.id}">you</option>
                    <c:forEach var = "i" items ="${userInGroup}">
                        <option value="${i.id}">${i.userName}</option>
                    </c:forEach>
                </select>
            </div>

            <input type="hidden" name="myhidCreate" value="${user.id}"/>
            <input type="hidden" id="myhidStars" name="star" value="${user.id}"/>

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
                <th class="sortStyle">Name</th>
                <th class="sortStyle">Due</th>
                <th class="sortStyle">Category</th>
                <th class="sortStyle">Priority</th>
                <th class="sortStyle">Assign To</th>
                <th class="sortStyle">Created By</th>
                <th class="sortStyle">Status</th>
                <th>Actions</th>
            </tr>

            </thead>
            <tbody>

            <c:forEach var = "i" items ="${taskList}">
                <tr>
                    <td>${i.get("task")}</td>
                    <td>${i.get("dueDate")}</td>
                    <td>${i.get("category")}</td>
                    <td><c:forEach var="no" begin="1" end="${i.get('priority')}">
                        <span class="fa fa-star checked"></span>
                    </c:forEach>
                        <c:forEach var="no" begin="${i.get('priority')}" end="5">
                            <span class="fa fa-star"></span>
                        </c:forEach></td>
                    <td>${i.get("AssUser")[0].get("userName")}</td>
                    <td>${i.get("CrUser")[0].get("userName")}</td>
                    <td>${i.get("status")}</td>
                    <td>
                        <a href="#" class="editRow" data-task-id="${i.get('id')}">Edit</a>
                        <a href="#" class="completeRow" data-task-id="${i.get('id')}">Complete</a>
                        <a href="#" class="deleteRow" data-task-id="${i.get('id')}">Delete</a>
                    </td>


                </tr>
            </c:forEach>




            <%--<c:forEach var = "i" items ="${taskList}">
                <tr>
                    <td>${i.task}</td>
                    <td>${i.dueDate}</td>
                    <td>${i.category}</td>
                    <td>
                        <c:forEach var="no" begin="1" end="${i.priority}">
                            <span class="fa fa-star checked"></span>
                        </c:forEach>
                        <c:forEach var="no" begin="${i.priority}" end="5">
                            <span class="fa fa-star"></span>
                        </c:forEach>

                    </td>
                    <td>${i.assignUser}</td>
                    <td>${i.createUser}</td>
                    <td>${i.status}</td>
                    <td>
                        <a href="#" class="editRow" data-task-id="{{= id}}">Edit</a>
                        <a href="#" class="completeRow" data-task-id="{{= id}}">Complete</a>
                        <a href="#" class="deleteRow" data-task-id="{{= id}}">Delete</a>
                    </td>

                </tr>
            </c:forEach>--%>
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
	<%--<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= priority}}</td>--%>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>
        {{var abc= priority}}
        {{var xyz=5- priority}}
        {{each(i) Array.apply(null, {length: abc }).map(Number.call, Number)}}
        <span class="fa fa-star checked"></span>
        {{/each}}
        {{each(i) Array.apply(null, {length: xyz }).map(Number.call, Number)}}
            <span class="fa fa-star"></span>
        {{/each}}
    </td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= AssUser[0].userName}}
	</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>{{= CrUser[0].userName}}</td>
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

