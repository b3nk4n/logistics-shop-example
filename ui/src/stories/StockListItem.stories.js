import React from 'react';

import StockListItem from '../components/stockListItem';

export default {
  title: 'Components/Stock List Item',
  component: StockListItem
};

const Template = (args) => <StockListItem {...args} />;

export const Default = Template.bind({});
Default.args = {
 item: {
   title: 'Title',
   sku: 'ABCDEFGH12345',
   amount: 23,
   change: 5
 }
};
