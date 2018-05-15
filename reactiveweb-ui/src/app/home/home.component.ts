import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../customer.service';
import { Customer } from '../customer';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';

@Component({
    styleUrls: ['./home.component.scss'],
    templateUrl: './home.component.html'

})
export class HomeComponent implements OnInit {
    customers: String[] = [];
    customer: Customer;

    // customers = [{ id : "1", name: "arjun", creation: new Date(), color: "red"}];
    welcomeMessage = "Reactive App Demo!";
    constructor(private customerService: CustomerService, private router: Router) {
    }

    ngOnInit() {
        this.loadData();
    }

    loadData(): void {
        this.customer = new Customer();
        this.customerService.getCustomers().subscribe(data => {
            console.log(data);
            this.customers = data;
        });
    }
    saveCustomer(): void {
        this.customerService.saveCustomer(this.customer)
            .subscribe(data => {
                console.log(data);
                this.loadData();
            });
    };
}

