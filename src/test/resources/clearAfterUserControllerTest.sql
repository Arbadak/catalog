BEGIN;
delete from doc_data where doc_data.id in (
select user.document_id from user where user.office_emp_id in (
select office.id from office where office.organization_id=(select organization.id from organization where organization.name='ООО Светлое будущее')));

delete from user where user.office_emp_id in (
select office.id from office where office.organization_id=(select organization.id from organization where organization.name='ООО Светлое будущее'));

delete from office where office.organization_id=(select organization.id from organization where organization.name='ООО Светлое будущее');

delete from organization where organization.name='ООО Светлое будущее';

commit;