import Contact from '../interfaces/Contact';

export default function fetchContacts(): Promise<Contact[]> {
  const contacts: Contact[] = [
    {
      name: 'Tony',
      address: 'Malibu',
      phones: {
        home: { number: 1211222333 },
        office: { number: 123122412 },
      },
    },
    {
      name: 'Banner',
      address: 'New York',
      phones: {
        home: { number: 769876 },
      },
    },
    {
      name: '마동석',
      address: '서울특별시 강남구',
      phones: {
        home: { number: 34567832 },
        studio: { number: 237247234 },
      },
    },
    {
      name: '광진소방서',
      address: '서울특별시 광진구',
      phones: {
        home: { number: 2119 },
        119: { number: 82119 },
      },
    },
  ];

  return new Promise((resolve) => {
    setTimeout(() => resolve(contacts), 2000);
  });
}
