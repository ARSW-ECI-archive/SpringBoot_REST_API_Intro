/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
//@RequestMapping(value = "/orders")
public class OrdersAPIController {

    private Map<Integer, Order> tableOrders = new HashMap<Integer, Order>();
    ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    RestaurantOrderServices data;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoOrders() {
        try {
            for (Integer i : data.getTablesWithOrders()){
                tableOrders.put(i, data.getTableOrder(i));
            }
            
            ArrayNode tables = mapper.createArrayNode();
            
            for (Map.Entry<Integer, Order> orderN : tableOrders.entrySet()) {
                ObjectNode order = mapper.createObjectNode();
                order.put("table", orderN.getValue().getTableNumber());
                
                ArrayNode products = mapper.createArrayNode();
                for (String p : orderN.getValue().getOrderedDishes()){
                    ObjectNode product = mapper.createObjectNode();
                    product.put("product", p);
                    product.put("amount", orderN.getValue().getDishOrderedAmount(p));
                    products.add(product);
                }
                order.put("dishes", products);
                tables.add(order);
            }
            
            return new ResponseEntity<>(tables , HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex, HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value="/orders/{tableId}", method=RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoOrderTable(@PathVariable Integer tableId){
        try{
            ArrayNode tableOrder = mapper.createArrayNode();
            ObjectNode table= mapper.createObjectNode();
            
            table.put("table", tableId);
            ArrayNode products = mapper.createArrayNode();
            for (String p : data.getTableOrder(tableId).getOrderedDishes()){
                    ObjectNode product = mapper.createObjectNode();
                    product.put("product", p);
                    product.put("amount", data.getTableOrder(tableId).getDishOrderedAmount(p));
                    products.add(product);
                }
            table.put("dishes", products);
            
            tableOrder.add(table);
            
            return new ResponseEntity<>(tableOrder,HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex, HttpStatus.NOT_FOUND);
        }
    }
    
}
