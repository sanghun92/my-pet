// ** React Imports
// ** MUI Imports
import { SvgIconProps } from '@mui/material';
import { OverridableComponent } from '@mui/material/OverridableComponent';
import { SvgIconTypeMap } from '@mui/material/SvgIcon/SvgIcon';

interface UserIconProps {
  iconProps?: SvgIconProps;
  icon?: OverridableComponent<SvgIconTypeMap> & { muiName: string };
}

const UserIcon = (props: UserIconProps) => {
  // ** Props
  const { icon, iconProps } = props;

  const IconTag = icon;

  let styles;

  /* styles = {
    color: 'red',
    fontSize: '2rem'
  } */

  // @ts-ignore
  return <IconTag {...iconProps} style={{ ...styles }} />;
};

export default UserIcon;
