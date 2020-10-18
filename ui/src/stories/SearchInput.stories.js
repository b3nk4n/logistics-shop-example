import React from 'react';

import SearchInput from '../components/searchInput';

export default {
  title: 'Components/Search Input',
  component: SearchInput
};

const Template = (args) => <SearchInput {...args} />;

export const Default = Template.bind({});
Default.args = {
  value: '',
  placeholder: 'Search...'
};
