$(function() {
    var SORT    = 'sort'    ;
    var ASC     = 'asc'     ;
    var DESC    = 'desc'    ;
    var UNSORT  = 'unsort'  ;

    var config  = {
        defaultColumn:  0,
        defaultOrder:   'asc',
        styles:         {
            'sort'  :   'sortStyle',
            'asc'   :   'ascStyle',
            'desc'  :   'descStyle',
            'unsort':   'unsortStyle'
        },
        selector:        function(tbody, column) {
            var groups = [];

            $.each($(tbody).find('tr'), function(index, tr) {
                var td = $(tr).find('td')[column];

                groups.push({
                    'values':   [ tr ],
                    'key'   :   $(td).text()
                });
            });

            return groups;
        },
        comparator:        function(group1, group2) {
            return group1.key.localeCompare(group2.key);
        }
    };

    function getTableHeaders(table) {
        return $(table).find('thead > tr > th');
    }

    function getSortableTableHeaders(table) {
        return getTableHeaders(table).filter(function(){
            return $(this).hasClass(config.styles[SORT]);
        });
    }

    function changeOrder(table, column) {
        var sortedHeader = getTableHeaders(table).filter(function() {
            return $(this).hasClass(config.styles[ASC]) || $(this).hasClass(config.styles[DESC]);
        });

        var sortOrder = config.defaultOrder;
        if (sortedHeader.hasClass(config.styles[ASC ])) {
            sortOrder = ASC;
        }
        if (sortedHeader.hasClass(config.styles[DESC])) {
            sortOrder = DESC;
        }

        var th = getTableHeaders(table)[column];

        if (th === sortedHeader[0]) {
            if (sortOrder === ASC) {
                sortOrder = DESC;
            } else {
                sortOrder = ASC;
            }
        }

        var headers = getSortableTableHeaders(table);
        headers.removeClass(config.styles[ASC   ]);
        headers.removeClass(config.styles[DESC  ]);
        headers.addClass(   config.styles[UNSORT]);

        $(th).removeClass(config.styles[UNSORT]);
        $(th).addClass(config.styles[sortOrder]);

        var tbody = $(table).find('tbody')[0];
        var groups = config.selector(tbody, column);

        // Sorting
        groups.sort(function(a, b){
            var res = config.comparator(a, b);
            return sortOrder == ASC ? res : -1 * res;
        });

        // Change order
        $.each(groups, function(i, trList) {
            $.each(trList.values, function(j, tr) {
                tbody.append(tr);
            });
        });
    }

    $('#tblTasks').each(function() {
        var selTbl = this;
        // Add click listener to the header
        getSortableTableHeaders(selTbl).each(function(j, th) {
            $(th).click(function(event) {
                var clickColumn = $.inArray(event.currentTarget, getTableHeaders(selTbl));

                changeOrder(selTbl, clickColumn);
            });
        });

        // Initialize table sort
        changeOrder(selTbl, config.defaultColumn);

    });
});