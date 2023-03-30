// ** React Imports
import { Fragment } from 'react';
// ** Icons Imports
import { Button, ButtonGroup } from '@mui/material';
import Link from 'next/link';
import { NavLink } from '@/core/layouts/types';

export interface AnonymousDropdownProps {
  anonymousNavItems: NavLink[];
}

const AnonymousDropdown = ({ anonymousNavItems }: AnonymousDropdownProps) => {
  return (
    <Fragment>
      <ButtonGroup variant='text' aria-label='text button group'>
        {anonymousNavItems?.map((item, index: number) => {
          return (
            <Link key={index} passHref legacyBehavior href={item === undefined ? '/' : `${item.path}`}>
              <Button component='a' {...(item.openInNewTab ? { target: '_blank' } : null)}>
                {item.title}
              </Button>
            </Link>
          );
        })}
      </ButtonGroup>
    </Fragment>
  );
};

export default AnonymousDropdown;
