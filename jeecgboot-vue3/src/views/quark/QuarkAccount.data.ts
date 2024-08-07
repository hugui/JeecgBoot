import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '头像',
    align:"center",
    dataIndex: 'avatar',
    customRender:render.renderImage,
   },
   {
    title: '昵称',
    align:"center",
    dataIndex: 'nickname'
   },
   {
    title: '状态',
    align:"center",
    dataIndex: 'status'
   },
   {
    title: 'Cookie',
    align:"center",
    dataIndex: 'cookie'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '头像',
    field: 'avatar',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
      },
  },
  {
    label: '昵称',
    field: 'nickname',
    component: 'Input',
  },
  {
    label: '状态',
    field: 'status',
    component: 'InputNumber',
  },
  {
    label: 'Cookie',
    field: 'cookie',
    component: 'InputTextArea',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  avatar: {title: '头像',order: 0,view: 'image', type: 'string',},
  nickname: {title: '昵称',order: 1,view: 'text', type: 'string',},
  status: {title: '状态',order: 2,view: 'number', type: 'number',},
  cookie: {title: 'Cookie',order: 3,view: 'textarea', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}