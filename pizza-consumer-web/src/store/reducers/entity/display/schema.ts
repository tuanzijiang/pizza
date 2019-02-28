
export interface DisplayEntity {
  id?: string;
  tag_name: string;
  tag_props: {
    [props: string]: any;
  };
  tag_children: DisplayEntity[];
}

export interface DisplayInfoEntity {
  display: {
    [props: string]: DisplayEntity;
  };
  display_order: string[];
  curr_display_id: string;
}
