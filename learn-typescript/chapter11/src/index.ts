import AddressBook from './models/AddressBook';

import PhoneType from './enums/PhoneType';

const addressBook = new AddressBook();

setTimeout(() => console.log(addressBook.findContactByName('마동석')), 3000);
setTimeout(() => console.log(addressBook.findContactByPhone(82119, PhoneType.Emergency)), 3000);
setTimeout(() => console.log(addressBook.findContactByPhone(123122412, PhoneType.Office)), 3000);
