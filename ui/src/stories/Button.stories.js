import React from 'react';

import Button from '../components/button';

export default {
  title: 'Components/Button',
  component: Button
};

const Template = (args) => <Button {...args} />;

export const Dark = Template.bind({});
Dark.args = {
  title: 'Dark Button',
};
