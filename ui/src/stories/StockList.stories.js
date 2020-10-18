import React from 'react';

import StockList from '../components/stockList';

export default {
  title: 'Components/Stock List',
  component: StockList
};

const Template = (args) => <StockList {...args} />;

export const Default = Template.bind({});
Default.args = {
 items: [
     {
         title: 'Skateboard',
         sku: 'SKB12345',
         amount: 11,
         change: 4
     },
     {
         title: 'Snowboard',
         sku: 'SNB12345',
         amount: 9,
         change: -2
     },
     {
         title: 'Scooter',
         sku: 'SCO12345',
         amount: 123,
         change: 0
     }
 ]
};
