import React from 'react';

import IndicatorIcon, {IconType} from '../components/indicatorIcon';

export default {
  title: 'Components/Indicator Icon',
  component: IndicatorIcon
};

const Template = (args) => <IndicatorIcon {...args} />;

export const Default = Template.bind({});
Default.args = {
  iconType: IconType.default
};

export const Increase = Template.bind({});
Increase.args = {
  iconType: IconType.increase
};

export const Decrease = Template.bind({});
Decrease.args = {
  iconType: IconType.decrease
};
