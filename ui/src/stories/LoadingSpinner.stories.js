import React from 'react';

import LoadingSpinner from '../components/loadingSpinner';

export default {
  title: 'Components/Loading Spinner',
  component: LoadingSpinner
};

const Template = (args) => <LoadingSpinner {...args} />;

export const Default = Template.bind({});
