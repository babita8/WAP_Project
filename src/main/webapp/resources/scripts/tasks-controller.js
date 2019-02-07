tasksController = function() { 
	
	function errorLogger(errorCode, errorMessage) {
		console.log(errorCode +':'+ errorMessage);
	}
	
	var taskPage;
	var initialised = false;
	var taskListEdit = null;

    /**
	 * makes json call to server to get task list.
	 * currently just testing this and writing return value out to console
	 * 111917kl
     */
	function retrieveTasksServer(selUserId) {
        $.ajax("TaskServlet", {
            "type": "get",
			dataType: "json",
            "data": {
                "selUserId": selUserId
            }
        }).done(displayTasksServer.bind()); //need reference to the tasksController object
    }

    /**
	 * 111917kl
	 * callback for retrieveTasksServer
     * @param data
     */

    function displayTasksServer(data) { //this needs to be bound to the tasksController -- used bind in retrieveTasksServer 111917kl
    	tasksController.loadServerTasks(data);
    }
	
	function taskCountChanged() {
		var count = $(taskPage).find( '#tblTasks tbody tr').length;
		$('footer').find('#taskCount').text(count);
	}
	
	function clearTask() {
	//	$('#parent > input:text:not(".ignore")').val('');
		var stars = $('#stars li').parent().children('li.star');
		for (i = 0; i < stars.length; i++) {
			$(stars[i]).removeClass('selected');
		}
		$(taskPage).find('#allowToClear').fromObject({});
	}

	function setRateStar() {

		var rate = $("#myhidStars").val();

		var stars = $('#stars li').parent().children('li.star');

		for (i = 0; i < 5; i++) {
			$(stars[i]).removeClass('selected');
		}

		for (i = 0; i < rate; i++) {
			$(stars[i]).addClass('selected');
		}




	}
	
	function renderTable() {
		$.each($(taskPage).find('#tblTasks tbody tr'), function(idx, row) {
			var due = Date.parse($(row).find('[datetime]').text());
			if (due.compareTo(Date.today()) < 0) {
				$(row).addClass("overdue");
			} else if (due.compareTo((2).days().fromNow()) <= 0) {
				$(row).addClass("warning");
			}
		});
	}
	
	return {
		taskListSet : function(value)
		{
			taskListEdit = value;
		},
		init : function(page, callback) { 
			if (initialised) {
				callback()
			} else {
				taskPage = page;
				storageEngine.init(function() {
					storageEngine.initObjectStore('task', function() {
						callback();
					}, errorLogger) 
				}, errorLogger);	 				
				$(taskPage).find('[required="required"]').prev('label').append( '<span>*</span>').children( 'span').addClass('required');
				$(taskPage).find('tbody tr:even').addClass('even');
				
				$(taskPage).find('#btnAddTask').click(function(evt) {
					evt.preventDefault();
					$(taskPage).find('#taskCreation').removeClass('not');
				});

                /**	 * 11/19/17kl        */
                $(taskPage).find('#btnRetrieveTasks').change(function(evt) {
                    evt.preventDefault();
                    console.log('making ajax call');
                    retrieveTasksServer(this.value);
                });
				
				$(taskPage).find('#tblTasks tbody').on('click', 'tr', function(evt) {
					$(evt.target).closest('td').siblings().andSelf().toggleClass('rowHighlight');
				});

				$(taskPage).find('#tblTasks tbody').on('click', '.deleteRow',
					function(evt) {
						storageEngine.delete('task', $(evt.target).data().taskId,
							function() {
								$(evt.target).parents('tr').remove();
								taskCountChanged();
							}, errorLogger);

						var task = $(evt.target).data().taskId;
						console.log('delete task');

						console.log(task);

						$.ajax("/DeleteTaskServlet", {
							"type": "POST",
							data: {"taskid": task}

						}).done(displayTasksServer.bind());

					}
				);
				
				$(taskPage).find('#tblTasks tbody').on('click', '.editRow', 
					function(evt) {
					var taskId = $(evt.target).data().taskId;
					$("#taskForm #taskEditId").val(taskId);
					var editTask = null;
					$.each(Array.from(taskListEdit), function (index, task) {
						if (task._id === taskId) {
							console.log("Task List Edit");
							console.log(task);
							editTask = task;
						}else{
							console.log("Incorrect ID:" + task.id + " " +taskId );
						}

					});

					$(taskPage).find('#taskCreation').removeClass('not');
					//console.log(editTask.get("_id"))
						//$(taskPage).find('form').fromObject(editTask);
					$(taskPage).find('#allowToClear').fromObject(editTask);

					setRateStar();

					//$(taskPage).find('form').fromObject(task);
					// 	storageEngine.findById('task', $(evt.target).data().taskId, function(task) {
					// 		$(taskPage).find('form').fromObject(task);
					// 	}, errorLogger);
					}

				);
				
				$(taskPage).find('#clearTask').click(function(evt) {
					evt.preventDefault();
					clearTask();
				});
				
				$(taskPage).find('#tblTasks tbody').on('click', '.completeRow', function(evt) { 					
					storageEngine.findById('task', $(evt.target).data().taskId, function(task) {
						task.complete = true;
						storageEngine.save('task', task, function() {
							tasksController.loadTasks();
						},errorLogger);
					}, errorLogger);
				});
				
				$(taskPage).find('#saveTask').click(function(evt) {
					evt.preventDefault();
					if ($(taskPage).find('form').valid()) {
						var task = $(taskPage).find('form').toObject();
						console.log(task);
						storageEngine.save('task', task, function() {
							$(taskPage).find('#tblTasks tbody').empty();
							tasksController.loadTasks();
							clearTask();
							$(taskPage).find('#taskCreation').addClass('not');
						}, errorLogger);

						$.ajax("/EditTaskServlet", {
							"type": "POST",
							 data: task
						}).done(displayTasksServer.bind());

					}
				});
				initialised = true;
			}
		},
        /**
		 * 111917kl
		 * modification of the loadTasks method to load tasks retrieved from the server
         */
		loadServerTasks: function(tasks) {
            $(taskPage).find('#tblTasks tbody').empty();
            taskListEdit =tasks;
            $.each(tasks, function (index, task) {
                if (!task.complete) {
                    task.complete = false;
                }
                $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                taskCountChanged();
                console.log('about to render table with server tasks');
                //renderTable(); --skip for now, this just sets style class for overdue tasks 111917kl4
            });
		},
		loadTasks : function() {
			/*$(taskPage).find('#tblTasks tbody').empty();*/
			storageEngine.findAll('task', function(tasks) {
				tasks.sort(function(o1, o2) {
					return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
				});
				$.each(tasks, function(index, task) {
					if (!task.complete) {
						task.complete = false;
					}
					$('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
					taskCountChanged();
					renderTable();
				});
			}, errorLogger);
		} 
	} 
}();



$.extend($.tmpl.tag, {
	"var": {
		open: "var $1;"
	}
});



$(document).ready(function(){

	/* 1. Visualizing things on Hover - See next part for action on click */
	$('#stars li').on('mouseover', function(){
		var onStar = parseInt($(this).data('value'), 10); // The star currently mouse on

		// Now highlight all the stars that's not after the current hovered star
		$(this).parent().children('li.star').each(function(e){
			if (e < onStar) {
				$(this).addClass('hover');
			}
			else {
				$(this).removeClass('hover');
			}
		});

	}).on('mouseout', function(){
		$(this).parent().children('li.star').each(function(e){
			$(this).removeClass('hover');
		});
	});


	/* 2. Action to perform on click */
	$('#stars li').on('click', function(){
		var onStar = parseInt($(this).data('value'), 10); // The star currently selected

		var stars = $(this).parent().children('li.star');

		for (i = 0; i < stars.length; i++) {
			$(stars[i]).removeClass('selected');
		}

		for (i = 0; i < onStar; i++) {
			$(stars[i]).addClass('selected');
		}

		$("#myhidStars").val(onStar);

	});


});


