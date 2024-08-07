import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '订阅名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '夸克账号ID',
    align:"center",
    dataIndex: 'accountId'
   },
   {
    title: '分享链接',
    align:"center",
    dataIndex: 'shareUrl'
   },
   {
    title: '保存的文件夹',
    align:"center",
    dataIndex: 'toDirFid'
   },
   {
    title: '订阅的文件夹',
    align:"center",
    dataIndex: 'sourceDirFid'
   },
   {
    title: '文件名前缀',
    align:"center",
    dataIndex: 'prefixName'
   },
   {
    title: '状态',
    align:"center",
    dataIndex: 'status'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '订阅名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入订阅名称!'},
          ];
     },
  },
  {
    label: '夸克账号ID',
    field: 'accountId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入夸克账号ID!'},
          ];
     },
  },
  {
    label: '分享链接',
    field: 'shareUrl',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入分享链接!'},
          ];
     },
  },
  {
    label: '保存的文件夹',
    field: 'toDirFid',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入保存的文件夹!'},
          ];
     },
  },
  {
    label: '订阅的文件夹',
    field: 'sourceDirFid',
    component: 'Input',
  },
  {
    label: '文件名前缀',
    field: 'prefixName',
    component: 'Input',
  },
  {
    label: '状态',
    field: 'status',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入状态!'},
          ];
     },
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
  name: {title: '订阅名称',order: 0,view: 'text', type: 'string',},
  accountId: {title: '夸克账号ID',order: 1,view: 'number', type: 'number',},
  shareUrl: {title: '分享链接',order: 2,view: 'text', type: 'string',},
  toDirFid: {title: '保存的文件夹',order: 3,view: 'text', type: 'string',},
  sourceDirFid: {title: '订阅的文件夹',order: 4,view: 'text', type: 'string',},
  prefixName: {title: '文件名前缀',order: 5,view: 'text', type: 'string',},
  status: {title: '状态',order: 6,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}