var order1 = {
    "id": "para",
    "tableNumber": 1,
    "orderAmountsMap": [
        {"product": "PIZZA", "quantity": 5},
        {"product": "HOTDOG", "quantity": 2},
        {"product": "BEER", "quantity": 7}
    ]
};

var order2 = {
    "id": 2,
    "tableNumber": 1,
    "orderAmountsMap": [
        {"product": "PIZZA", "quantity": 10},
        {"product": "HOTDOG", "quantity": 10},
        {"product": "BEER", "quantity": 10}
    ]
};

var order3 = {
    "id": 3,
    "tableNumber": 1,
    "orderAmountsMap": [
        {"product": "PIZZA", "quantity": 5},
        {"product": "HOTDOG", "quantity": 2},
        {"product": "BEER", "quantity": 7}
    ]
};

var order4 = {
    "id": 4,
    "tableNumber": 1,
    "orderAmountsMap": [
        {"product": "PIZZA", "quantity": 10},
        {"product": "HOTDOG", "quantity": 10},
        {"product": "BEER", "quantity": 10}
    ]
};

//var ordersList = [order1, order2, order3, order4];

axios.get('/orders')
        .then(function (response) {
            console.log(response);
            loadOrders(Object.entries(response['data']));
        })
        .catch(function (error) {
            console.log(error);
            alert("There is a problem with our servers. We apologize for the inconvince, please try again later.    ");
        });

function addOrder(order) {
    var main = document.getElementById('orders');

    var p = document.createElement('p');

    var tbl = document.createElement('table');
    tbl.id = order.id;

    var trAttributes = document.createElement('tr');

    var th1 = document.createElement('th');
    th1.appendChild(document.createTextNode('Product'));
    var th2 = document.createElement('th');
    th2.appendChild(document.createTextNode('Quantity'));

    trAttributes.appendChild(th1);
    trAttributes.appendChild(th2);

    tbl.appendChild(trAttributes);
    console.log(order);
    for (var i = 0; i < order.orderAmountsMap.length; i++) {
        var tr = document.createElement('tr');
        for (var j = 0; j < 2; j++) {
            var td = document.createElement('td');
            if (j == 0) {
                td.appendChild(document.createTextNode(order.orderAmountsMap[i].product));
            } else {
                td.appendChild(document.createTextNode(order.orderAmountsMap[i].quantity));
            }
            tr.appendChild(td);
        }
        tbl.appendChild(tr);
        main.appendChild(tbl);
    }
    main.appendChild(p);
    main.appendChild(tbl)
}

function removeOrderById(i) {
    var main = document.getElementById('orders');

    var objectToRemove = document.getElementById(i);
    console.log(objectToRemove);
    main.removeChild(objectToRemove);
}

function loadOrders(ordersList) {
    console.log(ordersList.length);
    console.log(ordersList);
    for (var i = 0; i < ordersList.length; i++) {
        console.log(ordersList[i]);
        addOrder(ordersList[i][1]);
    }
}

/*addOrder(order1);
 addOrder(order2);*/
