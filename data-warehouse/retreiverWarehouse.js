const ajax = require('ajax-request');

let url = 'https://api.energidataservice.dk/datastore_search'
let elementsStored = 0;


var data = {
    resource_id: 'electricityprodex5minrealtime',
    limit: 5
};

ajax({
    url,
    data: data,
    dataType: 'jsonp',
    success: function(data) {
        alert('Total results found: ' + data.result.total)
    }
});

