import React from 'react';

import ChangeIndicator from '../components/changeIndicator';

export default {
  title: 'Components/Change Indicator',
  component: ChangeIndicator
};

const Template = (args) => <ChangeIndicator {...args} />;

export const NoChange = Template.bind({});
NoChange.args = {
  change: 0
};

export const PositiveChange = Template.bind({});
PositiveChange.args = {
  change: 3
};

export const NegativeChange = Template.bind({});
NegativeChange.args = {
  change: -2
};
