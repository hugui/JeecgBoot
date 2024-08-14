import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '订阅ID',
    align:"center",
    dataIndex: 'subscribeId'
   },
   {
    title: '文件ID',
    align:"center",
    dataIndex: 'fid'
   },
   {
    title: '源文件ID',
    align:"center",
    dataIndex: 'sourceFid'
   },
   {
    title: '原始文件名',
    align:"center",
    dataIndex: 'fileName'
   },
   {
    title: '文件名',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '文件类型',
    align:"center",
    dataIndex: 'fileType'
   },
   {
    title: '文件夹ID',
    align:"center",
    dataIndex: 'dirFid'
   },
   {
    title: '是否是文件夹',
    align:"center",
    dataIndex: 'isDir'
   },
   {
    title: '状态',
    align:"center",
    dataIndex: 'status'
   },
  {
    title: '创建时间',
    align: 'center',
    dataIndex: 'createTime'
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '订阅ID',
    field: 'subscribeId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入订阅ID!'},
          ];
     },
  },
  {
    label: '文件ID',
    field: 'fid',
    component: 'Input',
  },
  {
    label: '源文件ID',
    field: 'sourceFid',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入源文件ID!'},
          ];
     },
  },
  {
    label: '原始文件名',
    field: 'fileName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入原始文件名!'},
          ];
     },
  },
  {
    label: '文件名',
    field: 'name',
    component: 'Input',
  },
  {
    label: '文件类型',
    field: 'fileType',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入文件类型!'},
          ];
     },
  },
  {
    label: '文件夹ID',
    field: 'dirFid',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入文件夹ID!'},
          ];
     },
  },
  {
    label: '是否是文件夹',
    field: 'isDir',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否是文件夹!'},
          ];
     },
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
  subscribeId: {title: '订阅ID',order: 0,view: 'number', type: 'number',},
  fid: {title: '文件ID',order: 1,view: 'text', type: 'string',},
  sourceFid: {title: '源文件ID',order: 2,view: 'text', type: 'string',},
  fileName: {title: '原始文件名',order: 3,view: 'text', type: 'string',},
  name: {title: '文件名',order: 4,view: 'text', type: 'string',},
  fileType: {title: '文件类型',order: 5,view: 'number', type: 'number',},
  dirFid: {title: '文件夹ID',order: 6,view: 'text', type: 'string',},
  isDir: {title: '是否是文件夹',order: 7,view: 'number', type: 'number',},
  status: {title: '状态',order: 8,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
