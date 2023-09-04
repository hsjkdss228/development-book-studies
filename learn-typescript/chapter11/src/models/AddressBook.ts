import fetchContacts from '../functions/fetchContacts';

import Contact from '../interfaces/Contact';

import PhoneType from '../enums/PhoneType';

export default class AddressBook {
  contacts: Contact[] = [];

  constructor() {
    this.fetchData();
  }

  fetchData(): void {
    fetchContacts().then((response: Contact[]) => {
      this.contacts = response;
    });
  }

  findContactByName(name: string): Contact[] {
    return this.contacts.filter((contact) => contact.name === name);
  }

  findContactByAddress(address: string): Contact[] {
    return this.contacts.filter((contact) => contact.address === address);
  }

  findContactByPhone(phoneNumber: number, phoneType: PhoneType): Contact[] {
    return this.contacts.filter((contact) => {
      return (
        Object.keys(contact.phones).filter((key) => key === phoneType).length > 0 &&
        contact.phones[phoneType].number === phoneNumber
      );
    });
  }

  addContact(contact: Contact): void {
    this.contacts = [...this.contacts, contact];
  }

  displayListByName(): string[] {
    return this.contacts.map((contact) => contact.name);
  }

  displayListByAddress(): string[] {
    return this.contacts.map((contact) => contact.address);
  }
}
